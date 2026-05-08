package com.khoa2111.ordermanagement.order.api;

import com.khoa2111.ordermanagement.order.Order;
import com.khoa2111.ordermanagement.order.OrderItem;
import com.khoa2111.ordermanagement.order.OrderRepository;
import com.khoa2111.ordermanagement.product.ProductLockRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderService {
  private final OrderRepository orderRepository;
  private final ProductLockRepository productLockRepository;

  public OrderService(OrderRepository orderRepository, ProductLockRepository productLockRepository) {
    this.orderRepository = orderRepository;
    this.productLockRepository = productLockRepository;
  }

  @Transactional
  public Order placeOrder(PlaceOrderRequest request) {
    Order order = new Order();
    order.setCustomerId(request.customerId());

    BigDecimal total = BigDecimal.ZERO;

    for (var itemReq : request.items()) {
      var product = productLockRepository.findByIdForUpdate(itemReq.productId())
          .orElseThrow(() -> new IllegalArgumentException("Product not found: " + itemReq.productId()));

      if (product.getStockQuantity() < itemReq.quantity()) {
        throw new IllegalStateException("Insufficient stock for productId=" + product.getId()
            + ", stock=" + product.getStockQuantity() + ", requested=" + itemReq.quantity());
      }

      // Trừ tồn kho (đang ở trong transaction + lock)
      product.setStockQuantity(product.getStockQuantity() - itemReq.quantity());

      OrderItem orderItem = new OrderItem();
      orderItem.setProduct(product);
      orderItem.setQuantity(itemReq.quantity());
      orderItem.setPriceAtPurchase(product.getPrice());

      order.addItem(orderItem);

      total = total.add(product.getPrice().multiply(BigDecimal.valueOf(itemReq.quantity())));
    }

    order.setTotalAmount(total);

    return orderRepository.save(order);
  }
}

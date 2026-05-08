package com.khoa2111.ordermanagement.order.api;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public PlaceOrderResponse placeOrder(@Valid @RequestBody PlaceOrderRequest request) {
    var order = orderService.placeOrder(request);
    return new PlaceOrderResponse(order.getId(), order.getStatus().name(), order.getTotalAmount());
  }
}

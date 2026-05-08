package com.khoa2111.ordermanagement.order.api;

import java.math.BigDecimal;

public record PlaceOrderResponse(
    Long orderId,
    String status,
    BigDecimal totalAmount
) {
}

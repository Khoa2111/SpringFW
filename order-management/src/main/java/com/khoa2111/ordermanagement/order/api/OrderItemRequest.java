package com.khoa2111.ordermanagement.order.api;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderItemRequest(
    @NotNull Long productId,
    @Min(1) long quantity
) {
}

package com.khoa2111.ordermanagement.product.api;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateProductRequest(
    @NotBlank String name,
    @NotNull BigDecimal price,
    @Min(0) long stockQuantity
) {
}

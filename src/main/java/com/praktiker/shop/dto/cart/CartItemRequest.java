package com.praktiker.shop.dto.cart;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CartItemRequest(

        @NotNull(message = "Product ID cannot be null")
        Long productId,

        @NotNull(message = "Quantity cannot be null")
        @DecimalMin(value = "0.1", message = "Quantity must be at least 0.1")
        BigDecimal quantity
) {
}

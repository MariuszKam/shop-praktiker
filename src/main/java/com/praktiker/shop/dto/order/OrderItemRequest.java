package com.praktiker.shop.dto.order;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class OrderItemRequest {

    @NotNull(message = "Product ID cannot be null")
    private Long productId;

    @NotNull(message = "Quantity cannot be null")
    @DecimalMin(value = "0.1", message = "Quantity must be at least 0.1")
    private BigDecimal quantity;
}

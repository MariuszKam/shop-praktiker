package com.praktiker.shop.dto.product.stock;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@RequiredArgsConstructor
public class ProductStockRequest {

    @NotNull(message = "Product cannot be null")
    private Long productId;

    @NotNull
    @DecimalMin(value = "1.0", message = "Cannot be smaller than 1")
    private BigDecimal amount;
}

package com.praktiker.shop.dto.product;

import com.praktiker.shop.entities.product.Unit;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
public class ProductCreateRequest {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotNull
    @DecimalMin(value = "0.1", message = "Quantity must be at least 0.1")
    private BigDecimal price;

    @NotNull
    private Unit unit;

    @NotNull
    @DecimalMin(value = "1.0", message = "Quantity must be at least 1.0")
    private BigDecimal amount;

    @NotNull(message = "Product Type cannot be null")
    private Long productTypeId;

}

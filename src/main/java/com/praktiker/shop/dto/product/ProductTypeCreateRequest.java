package com.praktiker.shop.dto.product;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ProductTypeCreateRequest {

    @NotBlank(message = "Name cannot be blank")
    private String name;
}


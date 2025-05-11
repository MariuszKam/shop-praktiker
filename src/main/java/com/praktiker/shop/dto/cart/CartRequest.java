package com.praktiker.shop.dto.cart;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CartRequest(

        @NotNull(message = "List of items cannot be null")
        @Size(min = 1, message = "Order must contain at least one item")
        List<CartItemRequest> items
) {
}

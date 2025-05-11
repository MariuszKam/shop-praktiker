package com.praktiker.shop.dto.cart;

import java.math.BigDecimal;
import java.util.List;

public record CartResponse(

        Long id,

        String username,

        BigDecimal totalPrice,

        List<CartItemResponse> items
) {
}

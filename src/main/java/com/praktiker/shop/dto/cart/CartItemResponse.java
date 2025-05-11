package com.praktiker.shop.dto.cart;

import java.math.BigDecimal;

public record CartItemResponse(

        Long productId,

        String productName,

        BigDecimal quantity,

        BigDecimal pricePerUnit
) {
}

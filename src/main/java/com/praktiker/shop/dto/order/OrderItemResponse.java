package com.praktiker.shop.dto.order;

import java.math.BigDecimal;

public record OrderItemResponse(Long productId, String productName, BigDecimal quantity, BigDecimal pricePerUnit) {
}

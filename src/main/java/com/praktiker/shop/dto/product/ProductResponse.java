package com.praktiker.shop.dto.product;

import java.math.BigDecimal;

public record ProductResponse(Long id, String name, BigDecimal price, String productType) {
}

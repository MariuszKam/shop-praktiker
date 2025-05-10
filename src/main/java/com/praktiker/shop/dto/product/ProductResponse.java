package com.praktiker.shop.dto.product;

import com.praktiker.shop.entities.product.Unit;

import java.math.BigDecimal;

public record ProductResponse(Long id, String name, BigDecimal price, Unit unit, String productType) {
}

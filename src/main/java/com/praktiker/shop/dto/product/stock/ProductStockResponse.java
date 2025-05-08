package com.praktiker.shop.dto.product.stock;

import com.praktiker.shop.entities.product.Unit;

import java.math.BigDecimal;

public record ProductStockResponse(String name, Unit unit, String productType, BigDecimal amount) {
}

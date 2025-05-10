package com.praktiker.shop.exceptions;

public class ProductStockNotFoundException extends RuntimeException {
    public ProductStockNotFoundException(String message) {
        super(message);
    }
}

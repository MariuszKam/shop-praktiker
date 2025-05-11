package com.praktiker.shop.exceptions.product;

public class ProductTypeNotFoundException extends RuntimeException {

    public ProductTypeNotFoundException(String message) {
        super(message);
    }
}

package com.praktiker.shop.exceptions;

public class ProductTypeNotFoundException extends RuntimeException {

    public ProductTypeNotFoundException(String message) {
        super(message);
    }
}

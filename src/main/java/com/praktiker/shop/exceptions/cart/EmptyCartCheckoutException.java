package com.praktiker.shop.exceptions.cart;

public class EmptyCartCheckoutException extends RuntimeException {
    public EmptyCartCheckoutException(String message) {
        super(message);
    }
}

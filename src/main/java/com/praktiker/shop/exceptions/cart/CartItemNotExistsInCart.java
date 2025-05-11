package com.praktiker.shop.exceptions.cart;

public class CartItemNotExistsInCart extends RuntimeException {
    public CartItemNotExistsInCart(String message) {
        super(message);
    }
}

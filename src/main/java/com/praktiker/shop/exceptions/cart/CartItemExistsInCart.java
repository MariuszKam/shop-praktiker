package com.praktiker.shop.exceptions.cart;

public class CartItemExistsInCart extends RuntimeException {
    public CartItemExistsInCart(String message) {
        super(message);
    }
}

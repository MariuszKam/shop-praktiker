package com.praktiker.shop.services;

import com.praktiker.shop.persistance.cart.CartItemRepository;
import com.praktiker.shop.persistance.cart.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;
}

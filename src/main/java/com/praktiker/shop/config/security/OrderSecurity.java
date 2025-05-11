package com.praktiker.shop.config.security;

import com.praktiker.shop.persistance.order.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class OrderSecurity {

    private final OrderRepository orderRepository;

    public boolean isOwner(Long orderId, String username) {
        return orderRepository.findById(orderId)
                              .map(order -> order.getUser().getUsername().equals(username))
                              .orElse(false);
    }
}

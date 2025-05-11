package com.praktiker.shop.dto.order;

import com.praktiker.shop.entities.order.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponse(

        Long id,

        String username,

        BigDecimal totalAmount,

        OrderStatus status,

        List<OrderItemResponse> items) {
}

package com.praktiker.shop.mappers;

import com.praktiker.shop.dto.order.OrderCreateRequest;
import com.praktiker.shop.dto.order.OrderResponse;
import com.praktiker.shop.entities.order.Order;

import java.util.List;

public class OrderMapper {

    /********************************* ORDERS RESPONSE *********************************/

    public static OrderResponse toResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getUser().getUsername(),
                order.getTotalPrice(),
                order.getOrderStatus().name(),
                OrderItemMapper.toResponse(order.getOrderItems()));
    }

    public static List<OrderResponse> toResponse(List<Order> orders) {
        return orders.stream().map(OrderMapper::toResponse).toList();
    }

    /********************************* ORDERS REQUESTS *********************************/

    public static OrderCreateRequest toRequest(Order order) {
        return new OrderCreateRequest(OrderItemMapper.toRequests(order.getOrderItems()));
    }
}

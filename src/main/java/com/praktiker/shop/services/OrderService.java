package com.praktiker.shop.services;

import com.praktiker.shop.entities.order.Order;
import com.praktiker.shop.entities.order.OrderStatus;
import com.praktiker.shop.exceptions.OrderNotFoundException;
import com.praktiker.shop.persistance.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() ->
                new OrderNotFoundException("Order with id: " + id + "not found"));
    }

    public List<Order> getOrdersByUsername(String username) {
        return orderRepository.findAllByUser_Username(username);
    }

    public Order creatOrder(Order order) {
        return orderRepository.save(order);
    }

    public void changeOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));

        if (!order.getOrderStatus().canTransitionTo(newStatus)) {
            throw new IllegalStateException("Invalid status transition: " + order.getOrderStatus() + " → " + newStatus);
        }

        order.setOrderStatus(newStatus);
        orderRepository.save(order);
    }
}

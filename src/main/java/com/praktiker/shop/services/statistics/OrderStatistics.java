package com.praktiker.shop.services.statistics;

import com.praktiker.shop.entities.order.Order;
import com.praktiker.shop.exceptions.OrderNotFoundException;
import com.praktiker.shop.persistance.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;

@Service
public class OrderStatistics {

    private OrderRepository orderRepository;

    public OrderStatistics(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public BigDecimal getAverageOrderSum() {
        List<Order> orders = orderRepository.findAll();

        if (orders.isEmpty()) {
            throw new OrderNotFoundException("Could not find average order. Order list is empty");
        }

        BigDecimal totalSum = orders.stream().map(Order::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalSum.divide(BigDecimal.valueOf(orders.size(), 2), RoundingMode.HALF_UP);
    }

    public Order getMostExpensive() {
        return orderRepository.findAll().stream()
                .max(Comparator.comparing(Order::getTotalPrice))
                .orElseThrow(() -> new OrderNotFoundException("Could not find most expensive order. Order list is empty"));
    }
}

package com.praktiker.shop.services.statistics;

import com.praktiker.shop.dto.order.OrderResponse;
import com.praktiker.shop.entities.order.Order;
import com.praktiker.shop.exceptions.order.OrderNotFoundException;
import com.praktiker.shop.mappers.order.OrderMapper;
import com.praktiker.shop.persistance.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderStatisticsService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    public BigDecimal getAverageOrderSum() {
        List<Order> orders = orderRepository.findAll();

        if (orders.isEmpty()) {
            throw new OrderNotFoundException("Could not find average order. Order list is empty");
        }

        BigDecimal totalSum = orders.stream()
                                    .map(Order::getTotalPrice)
                                    .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalSum.divide(BigDecimal.valueOf(orders.size()), 2, RoundingMode.HALF_UP);
    }

    public OrderResponse getMostExpensive() {
        Order order = orderRepository.findAll().stream()
                                     .max(Comparator.comparing(Order::getTotalPrice))
                                     .orElseThrow(() -> new OrderNotFoundException(
                                             "Could not find most expensive order. Order list is empty"));

        return orderMapper.toResponse(order);
    }
}

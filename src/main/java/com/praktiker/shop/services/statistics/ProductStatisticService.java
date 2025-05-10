package com.praktiker.shop.services.statistics;

import com.praktiker.shop.dto.product.ProductResponse;
import com.praktiker.shop.entities.order.Order;
import com.praktiker.shop.entities.order.OrderItem;
import com.praktiker.shop.entities.product.Product;
import com.praktiker.shop.exceptions.OrderNotFoundException;
import com.praktiker.shop.mappers.ProductMapper;
import com.praktiker.shop.persistance.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductStatisticService {

    private final OrderRepository orderRepository;

    private final ProductMapper productMapper;

    public ProductResponse getBestseller() {
        List<Order> orders = orderRepository.findAll();

        if (orders.isEmpty()) {
            throw new OrderNotFoundException("Could not find bestseller. Order list is empty");
        }

        Product product = orders.stream()
                                .flatMap(order -> order.getOrderItems().stream())
                                .collect(Collectors.groupingBy(
                                        OrderItem::getProduct,
                                        Collectors.reducing(
                                                BigDecimal.ZERO,
                                                OrderItem::getQuantity,
                                                BigDecimal::add
                                        )
                                ))
                                .entrySet().stream()
                                .max(Map.Entry.comparingByValue())
                                .map(Map.Entry::getKey)
                                .orElseThrow(() -> new OrderNotFoundException(
                                        "Could not find any product during bestseller calculation"));

        return productMapper.toResponse(product);
    }
}

package com.praktiker.shop.services;

import com.praktiker.shop.dto.order.OrderCreateRequest;
import com.praktiker.shop.dto.order.OrderItemRequest;
import com.praktiker.shop.dto.order.OrderResponse;
import com.praktiker.shop.dto.order.OrderStatusUpdateRequest;
import com.praktiker.shop.entities.order.Order;
import com.praktiker.shop.entities.order.OrderItem;
import com.praktiker.shop.entities.order.OrderStatus;
import com.praktiker.shop.entities.product.Product;
import com.praktiker.shop.entities.user.User;
import com.praktiker.shop.exceptions.OrderNotFoundException;
import com.praktiker.shop.exceptions.ProductNotFoundException;
import com.praktiker.shop.exceptions.UserNotFoundException;
import com.praktiker.shop.mappers.OrderItemMapper;
import com.praktiker.shop.mappers.OrderMapper;
import com.praktiker.shop.persistance.OrderRepository;
import com.praktiker.shop.persistance.product.ProductRepository;
import com.praktiker.shop.persistance.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    private final OrderMapper orderMapper;

    private final OrderItemMapper orderItemMapper;

    @PreAuthorize("@orderSecurity.isOwner(#id, authentication.name)")
    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() ->
                                                                       new OrderNotFoundException(
                                                                               "Order with id: " + id + "not found"));

        return orderMapper.toResponse(order);
    }

    public List<OrderResponse> getOrdersByUsername(String username) {
        return orderMapper.toResponse(orderRepository.findAllByUser_Username(username));
    }

    @Transactional
    public OrderResponse createOrder(OrderCreateRequest request, String username) {
        User user = userRepository.findByUsername(username)
                                  .orElseThrow(() -> new UserNotFoundException("Username do not fit to any User"));

        Order order = Order.builder()
                           .orderStatus(OrderStatus.CREATED)
                           .user(user)
                           .build();

        List<Long> productIds = request.getItems()
                                       .stream()
                                       .map(OrderItemRequest::getProductId)
                                       .toList();

        List<Product> products = productRepository.findAllById(productIds);

        if (products.size() != productIds.size()) {
            throw new ProductNotFoundException("One or more products do not exist");
        }

        Map<Long, Product> productMap = products.stream()
                                                .collect(Collectors.toMap(Product::getId, Function.identity()));


        List<OrderItem> items = request.getItems().stream()
                                       .map(item -> orderItemMapper.toEntity(
                                               item,
                                               productMap.get(item.getProductId()),
                                               order
                                       )).toList();

        order.setOrderItems(items);

        orderRepository.save(order);

        return orderMapper.toResponse(order);
    }

    @Transactional
    public OrderResponse changeOrderStatus(Long orderId, OrderStatusUpdateRequest request) {
        Order order = orderRepository.findById(orderId)
                                     .orElseThrow(
                                             () -> new OrderNotFoundException("Order not found with ID: " + orderId));

        if (!order.getOrderStatus().canTransitionTo(request.getStatus())) {
            throw new IllegalStateException(
                    "Invalid status transition: " + order.getOrderStatus() + " → " + request.getStatus());
        }

        order.setOrderStatus(request.getStatus());

        orderRepository.save(order);

        return orderMapper.toResponse(order);
    }
}

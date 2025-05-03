package com.praktiker.shop.utilis.factories;

import com.praktiker.shop.dto.order.OrderCreateRequest;
import com.praktiker.shop.dto.order.OrderItemRequest;
import com.praktiker.shop.dto.order.OrderItemResponse;
import com.praktiker.shop.dto.order.OrderResponse;
import com.praktiker.shop.entities.order.Order;
import com.praktiker.shop.entities.order.OrderItem;
import com.praktiker.shop.entities.order.OrderStatus;
import com.praktiker.shop.entities.user.User;
import com.praktiker.shop.mappers.OrderItemMapper;
import com.praktiker.shop.mappers.OrderMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderTestFactory {

    /********************************* ORDERS *********************************/

    public static Order createOrder(Long id, List<OrderItem> orderItems, User user, OrderStatus orderStatus) {
        Order order = new Order();
        order.setId(id);
        order.setOrderItems(orderItems);
        order.setUser(user);
        order.setOrderStatus(orderStatus);
        return order;
    }

    public static Order createOrder(List<OrderItem> orderItems, User user) {
        return createOrder(1L, orderItems, user, OrderStatus.CREATED);
    }

    public static Order createOrder(List<OrderItem> orderItems) {
        return createOrder(orderItems, UserTestFactory.createUser());
    }

    public static Order createOrder(User user) {
        return createOrder(1L, OrderItemTestFactory.createOrderItemList(), user, OrderStatus.CREATED);
    }

    public static Order createOrder() {
        return createOrder(OrderItemTestFactory.createOrderItemList(), UserTestFactory.createUser());
    }

    public static List<Order> createOrdersForUser(User user) {
        return List.of(createOrder(1L, OrderItemTestFactory.createOrderItemList(1L), user, OrderStatus.CREATED),
                       createOrder(2L, OrderItemTestFactory.createOrderItemList(2L), user, OrderStatus.PAID),
                       createOrder(3L, OrderItemTestFactory.createOrderItemList(3L), user, OrderStatus.DELIVERED));
    }

    public static List<Order> createOrdersList(User user) {
        List<Order> orders = new ArrayList<>();

        List<OrderItem> itemListFirst = OrderItemTestFactory.createOrderItemList(1L, BigDecimal.ONE);
        orders.add(createOrder(itemListFirst, user));

        List<OrderItem> itemListSecond = OrderItemTestFactory.createOrderItemList(2L, BigDecimal.valueOf(2));
        orders.add(createOrder(itemListSecond, user));
        return orders;
    }

    /********************************* ORDERS RESPONSES *********************************/

    public static OrderResponse createResponse(Long orderId,
                                               String username,
                                               BigDecimal totalAmount,
                                               String status,
                                               List<OrderItemResponse> items) {

        return new OrderResponse(orderId, username, totalAmount, status, items);
    }

    public static OrderResponse createResponse(String username) {
        return createResponse(1L, username, BigDecimal.valueOf(220.99), "CREATED",
                              OrderItemTestFactory.createOrderItemResponseList());
    }

    public static OrderResponse createResponse(Order order) {
        return createResponse(order.getId(),
                              order.getUser().getUsername(),
                              order.getTotalPrice(),
                              order.getOrderStatus().name(),
                              OrderItemMapper.toResponse(order.getOrderItems()));
    }

    public static List<OrderResponse> createResponses(List<Order> orders) {
        return OrderMapper.toResponse(orders);
    }

    public static List<OrderResponse> createResponses(String username) {
        return List.of(createResponse(username), createResponse(username));
    }

    /********************************* ORDERS REQUESTS *********************************/

    public static OrderCreateRequest createRequest(List<OrderItemRequest> orderItemRequests) {
        return new OrderCreateRequest(orderItemRequests);
    }

    public static OrderCreateRequest createRequest(Order order) {
        return OrderMapper.toRequest(order);
    }

    public static OrderCreateRequest createRequest() {
        return createRequest(OrderItemTestFactory.createOrderItemRequestList());
    }

}

package com.praktiker.shop.utilis;

import com.praktiker.shop.entities.order.Order;
import com.praktiker.shop.entities.order.OrderItem;
import com.praktiker.shop.entities.order.OrderStatus;
import com.praktiker.shop.entities.user.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderTestFactory {

    public static Order createOrder(List<OrderItem> orderItems, User user, OrderStatus orderStatus) {
        Order order = new Order();
        order.setOrderItems(orderItems);
        order.setUser(user);
        order.setOrderStatus(orderStatus);
        return order;
    }

    public static Order createOrder(User user, OrderStatus status) {
        return createOrder(new ArrayList<>(), user, status);
    }

    public static Order createOrder(User user) {
        return createOrder(user, OrderStatus.CREATED);
    }

    public static Order createOrder(List<OrderItem> orderItems) {
        return createOrder(orderItems, UserTestFactory.createUser(), OrderStatus.CREATED);
    }

    public static List<Order> createOrdersForUser(User user) {
        List<Order> orders = new ArrayList<>();

        orders.add(createOrder(user, OrderStatus.CREATED));
        orders.add(createOrder(user, OrderStatus.PAID));
        orders.add(createOrder(user, OrderStatus.DELIVERED));

        return orders;
    }

    public static List<Order> createOrdersList(User user) {
        List<Order> orders = new ArrayList<>();

        List<OrderItem> itemListFirst = OrderItemTestFactory.createOrderItemList(1L, BigDecimal.ONE);
        orders.add(createOrder(itemListFirst, user, OrderStatus.CREATED));

        List<OrderItem> itemListSecond = OrderItemTestFactory.createOrderItemList(2L, BigDecimal.valueOf(2));
        orders.add(createOrder(itemListSecond, user, OrderStatus.CREATED));
        return orders;
    }
}

package com.praktiker.shop.utilis;

import com.praktiker.shop.entities.order.Order;
import com.praktiker.shop.entities.order.OrderStatus;
import com.praktiker.shop.entities.user.User;

import java.util.ArrayList;
import java.util.List;

public class OrderTestFactory {

    public static List<Order> createOrdersForUser(User user) {
        List<Order> orders = new ArrayList<>();

        orders.add(createOrder(user, OrderStatus.CREATED));
        orders.add(createOrder(user, OrderStatus.PAID));
        orders.add(createOrder(user, OrderStatus.DELIVERED));

        return orders;
    }

    public static Order createOrder(User user, OrderStatus status) {
        Order order = new Order();
        order.setUser(user);
        order.setOrderStatus(status);
        order.setOrderItems(new ArrayList<>());
        return order;
    }

    public static Order createOrder(User user) {
        return createOrder(user, OrderStatus.CREATED);
    }
}

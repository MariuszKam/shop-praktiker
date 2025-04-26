package com.praktiker.shop.services;

import com.praktiker.shop.entities.order.Order;
import com.praktiker.shop.entities.order.OrderStatus;
import com.praktiker.shop.entities.user.User;
import com.praktiker.shop.exceptions.OrderNotFoundException;
import com.praktiker.shop.persistance.OrderRepository;
import com.praktiker.shop.utilis.OrderTestFactory;
import com.praktiker.shop.utilis.UserTestFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @DisplayName("Part of testing getOrderById(Long id) - positive case")
    @Test
    public void shouldGetOrderById() {
        User user = UserTestFactory.createUser();

        Order order = OrderTestFactory.createOrder(user);
        order.setId(1L);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Order actual = orderService.getOrderById(1L);

        assertEquals(1L, actual.getId(), "ID is wrong!");
        assertEquals(OrderStatus.CREATED, actual.getOrderStatus(), "Order Status is wrong!");
        assertEquals(user, actual.getUser(), "User is wrong!");
    }

    @DisplayName("Part of testing getOrderById(Long id) - negative case")
    @Test
    public void shouldThrowWhenOrderNotFoundById() {
        Long notExistId = 2L;

        when(orderRepository.findById(notExistId)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.getOrderById(notExistId),
                "Should throw OrderNotFoundException for non existing ID");
    }

    @DisplayName("Testing getOrdersByUsername(String username)")
    @Test
    public void shouldGetOrderByUsername() {
        User user = UserTestFactory.createUser();

        List<Order> orders = OrderTestFactory.createOrdersForUser(user);

        when(orderRepository.findAllByUser_Username(user.getUsername())).thenReturn(orders);

        List<Order> actual = orderService.getOrdersByUsername(user.getUsername());

        assertFalse(actual.isEmpty(), "List should not be empty!");

        boolean allMatch = actual.stream()
                .allMatch(order -> order.getUser().getUsername().equals(user.getUsername()));

        assertTrue(allMatch, "Not all orders belong to the correct user!");
    }

    @DisplayName("Testing createOrder(Order order")
    @Test
    public void shouldCreateOrder() {
        User user = UserTestFactory.createUser();

        Order order = OrderTestFactory.createOrder(user);

        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Order actual = orderService.creatOrder(order);

        assertEquals(order.getOrderStatus(), actual.getOrderStatus(), "Order Status is wrong!");
        assertEquals(order.getUser(), actual.getUser(), "User is wrong for Order!");
    }

    @DisplayName("Part of testing changeOrderStatus(Long orderId, OrderStatus newStatus) - positive case")
    @Test
    public void shouldChangeOrderStatus() {
        User user = UserTestFactory.createUser();

        Order order = OrderTestFactory.createOrder(user);
        order.setId(1L);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        orderService.changeOrderStatus(1L, OrderStatus.PAID);

        assertEquals(OrderStatus.PAID, order.getOrderStatus(), "Order status did not changed!");
    }

    @DisplayName("Part of testing changeOrderStatus(Long orderId, OrderStatus newStatus) - negative case")
    @Test
    public void shouldThrowOrderNotFoundWhenChangingStatus() {
        Long notExist = 2L;

        when(orderRepository.findById(notExist)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.changeOrderStatus(2L, OrderStatus.PAID),
                "Should throw OrderNotFoundException for Change Status!");

    }

    @DisplayName("Part of testing changeOrderStatus(Long orderId, OrderStatus newStatus) - negative case")
    @Test
    public void shouldThrowIllegalStateExceptionWhenChangingStatus() {
        User user = UserTestFactory.createUser();

        Order order = OrderTestFactory.createOrder(user);
        order.setOrderStatus(OrderStatus.DELIVERED);
        order.setId(1L);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        assertThrows(IllegalStateException.class, () -> orderService.changeOrderStatus(1L, OrderStatus.CREATED),
                "Should throw IllegalStateException for change order backwards!");
    }
}

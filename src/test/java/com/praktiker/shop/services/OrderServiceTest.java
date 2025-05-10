package com.praktiker.shop.services;

import com.praktiker.shop.dto.order.OrderCreateRequest;
import com.praktiker.shop.dto.order.OrderResponse;
import com.praktiker.shop.dto.order.OrderStatusUpdateRequest;
import com.praktiker.shop.entities.order.Order;
import com.praktiker.shop.entities.order.OrderItem;
import com.praktiker.shop.entities.order.OrderStatus;
import com.praktiker.shop.entities.product.Product;
import com.praktiker.shop.entities.user.User;
import com.praktiker.shop.exceptions.OrderNotFoundException;
import com.praktiker.shop.mappers.OrderItemMapper;
import com.praktiker.shop.persistance.OrderRepository;
import com.praktiker.shop.persistance.product.ProductRepository;
import com.praktiker.shop.persistance.UserRepository;
import com.praktiker.shop.utilis.factories.OrderTestFactory;
import com.praktiker.shop.utilis.factories.UserTestFactory;
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
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderService orderService;

    @DisplayName("Part of testing getOrderById - positive case")
    @Test
    public void shouldGetOrderById() {
        Order order = OrderTestFactory.createOrder();

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        OrderResponse response = orderService.getOrderById(1L);

        assertEquals(1L, response.orderId(), "ID is not correct");
        assertEquals(order.getUser().getUsername(), response.username(), "Username is not correct");
        assertEquals(order.getTotalPrice(), response.totalAmount(), "Total amount price iis not same");
        assertEquals(OrderStatus.CREATED.name(), response.status(), "Order Status is not correct");
        assertEquals(OrderItemMapper.toResponse(order.getOrderItems()), response.items(), "Item list is not the same");
    }

    @DisplayName("Part of testing getOrderById(Long id) - negative case")
    @Test
    public void shouldThrowWhenOrderNotFoundById() {
        Long notExistId = 2L;

        when(orderRepository.findById(notExistId)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.getOrderById(notExistId),
                     "Should throw OrderNotFoundException for non existing ID");
    }

    @DisplayName("Testing getOrdersByUsername")
    @Test
    public void shouldGetOrderByUsername() {
        User user = UserTestFactory.createUser();

        List<Order> orders = OrderTestFactory.createOrders(user);

        when(orderRepository.findAllByUser_Username(user.getUsername())).thenReturn(orders);

        List<OrderResponse> actual = orderService.getOrdersByUsername(user.getUsername());

        assertFalse(actual.isEmpty(), "List should not be empty!");

        boolean allMatch = actual.stream()
                                 .allMatch(order -> order.username().equals(user.getUsername()));

        assertTrue(allMatch, "Not all orders belong to the correct user!");
    }

    @DisplayName("Testing createOrder")
    @Test
    public void shouldCreateOrder() {
        User user = UserTestFactory.createUser();

        Order order = OrderTestFactory.createOrder(user);

        OrderCreateRequest request = OrderTestFactory.createRequest(order);

        List<Product> products = order.getOrderItems().stream()
                                      .map(OrderItem::getProduct).toList();

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(productRepository.findAllById(anyCollection())).thenReturn(products);
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        OrderResponse response = orderService.createOrder(request, user.getUsername());

        assertEquals(order.getUser().getUsername(), response.username(), "User is not correct");
        assertEquals(order.getTotalPrice(), response.totalAmount(), "Total amount price iis not same");
        assertEquals(order.getOrderStatus().name(), response.status(), "Order Status is not correct");
        assertEquals(OrderItemMapper.toResponse(order.getOrderItems()), response.items(), "Item list is not the same");
    }

    @DisplayName("Part of testing changeOrderStatus - positive case")
    @Test
    public void shouldChangeOrderStatus() {
        Order order = OrderTestFactory.createOrder();

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        OrderStatusUpdateRequest updateRequest = new OrderStatusUpdateRequest(OrderStatus.PAID);

        OrderResponse response = orderService.changeOrderStatus(1L, updateRequest);

        assertEquals(updateRequest.getStatus().name(), response.status(), "Order status did not changed!");
    }

    @DisplayName("Part of testing changeOrderStatus - negative case")
    @Test
    public void shouldThrowOrderNotFoundWhenChangingStatus() {
        Long notExist = 2L;

        when(orderRepository.findById(notExist)).thenReturn(Optional.empty());

        OrderStatusUpdateRequest updateRequest = new OrderStatusUpdateRequest(OrderStatus.PAID);

        assertThrows(OrderNotFoundException.class, () -> orderService.changeOrderStatus(2L, updateRequest),
                     "Should throw OrderNotFoundException for Change Status!");

    }

    @DisplayName("Part of testing changeOrderStatus - negative case(Delivered to Created")
    @Test
    public void shouldThrowIllegalStateExceptionWhenChangingStatus() {
        User user = UserTestFactory.createUser();

        Order order = OrderTestFactory.createOrder(user);
        order.setOrderStatus(OrderStatus.DELIVERED);
        order.setId(1L);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        OrderStatusUpdateRequest updateRequest = new OrderStatusUpdateRequest(OrderStatus.CREATED);

        assertThrows(IllegalStateException.class, () -> orderService.changeOrderStatus(1L, updateRequest),
                     "Should throw IllegalStateException for change order backwards!");
    }
}

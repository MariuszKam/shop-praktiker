package com.praktiker.shop.services.integration;

import com.praktiker.shop.dto.order.OrderCreateRequest;
import com.praktiker.shop.dto.order.OrderResponse;
import com.praktiker.shop.dto.order.OrderStatusUpdateRequest;
import com.praktiker.shop.entities.order.Order;
import com.praktiker.shop.entities.order.OrderStatus;
import com.praktiker.shop.entities.user.User;
import com.praktiker.shop.exceptions.OrderNotFoundException;
import com.praktiker.shop.persistance.OrderRepository;
import com.praktiker.shop.persistance.UserRepository;
import com.praktiker.shop.services.OrderService;
import com.praktiker.shop.utilis.factories.OrderTestFactory;
import com.praktiker.shop.utilis.seeders.TestDataSeeder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Import(TestDataSeeder.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderServiceIT {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestDataSeeder testDataSeeder;

    @BeforeAll
    void init() {
        testDataSeeder.initializeDatabase();
    }

    @Test
    @DisplayName("should get order by id")
    void shouldGetOrderById() {
        Order existingOrder = orderRepository.findAll().getFirst();

        OrderResponse response = orderService.getOrderById(existingOrder.getId());

        assertEquals(existingOrder.getId(), response.orderId());
        assertEquals(existingOrder.getUser().getUsername(), response.username());
        assertEquals(existingOrder.getTotalPrice(), response.totalAmount());
        assertEquals(existingOrder.getOrderStatus().name(), response.status());
    }

    @Test
    @DisplayName("should throw when order not found")
    void shouldThrowWhenOrderNotFoundById() {
        Long nonExistentId = 999L;
        assertThrows(OrderNotFoundException.class, () -> orderService.getOrderById(nonExistentId));
    }

    @Test
    @DisplayName("should return orders by username")
    void shouldReturnOrdersByUsername() {
        String username = "Adam";

        List<OrderResponse> orders = orderService.getOrdersByUsername(username);

        assertThat(orders).isNotEmpty();
        assertThat(orders).allMatch(o -> o.username().equals(username));
    }

    @Test
    @DisplayName("should change order status")
    void shouldChangeOrderStatus() {
        Order order = orderRepository.findAll().getFirst();

        OrderStatusUpdateRequest updateRequest = new OrderStatusUpdateRequest(OrderStatus.PAID);

        OrderResponse updated = orderService.changeOrderStatus(order.getId(), updateRequest);

        assertEquals(OrderStatus.PAID.name(), updated.status());
    }

    @Test
    @DisplayName("should throw when changing status on non-existent order")
    void shouldThrowOnChangeStatusOfMissingOrder() {
        OrderStatusUpdateRequest updateRequest = new OrderStatusUpdateRequest(OrderStatus.PAID);

        assertThrows(OrderNotFoundException.class, () -> orderService.changeOrderStatus(999L, updateRequest));
    }

    @Test
    @DisplayName("should throw when changing status from DELIVERED back to CREATED")
    void shouldThrowWhenRevertingDeliveredStatus() {
        Order order = orderRepository.findAll().getFirst();
        order.setOrderStatus(OrderStatus.DELIVERED);
        orderRepository.save(order);

        OrderStatusUpdateRequest updateRequest = new OrderStatusUpdateRequest(OrderStatus.CREATED);

        assertThrows(IllegalStateException.class, () -> orderService.changeOrderStatus(order.getId(), updateRequest));
    }

    @Test
    @DisplayName("should create new order")
    void shouldCreateOrder() {
        User user = userRepository.findByUsername("Adam")
                                  .orElseThrow(() -> new RuntimeException("User not found"));

        Order templateOrder = orderRepository.findAll().getFirst();

        OrderCreateRequest request = OrderTestFactory.createRequest(templateOrder);

        OrderResponse created = orderService.createOrder(request, user.getUsername());

        assertThat(created.username()).isEqualTo(user.getUsername());
        assertThat(created.status()).isEqualTo(OrderStatus.CREATED.name());
        assertThat(created.totalAmount()).isEqualTo(templateOrder.getTotalPrice());
    }
}
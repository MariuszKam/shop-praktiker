package com.praktiker.shop.services.statistic;

import com.praktiker.shop.entities.order.Order;
import com.praktiker.shop.entities.order.OrderItem;
import com.praktiker.shop.entities.user.User;
import com.praktiker.shop.exceptions.OrderNotFoundException;
import com.praktiker.shop.persistance.OrderRepository;
import com.praktiker.shop.services.statistics.OrderStatistics;
import com.praktiker.shop.utilis.factories.OrderItemTestFactory;
import com.praktiker.shop.utilis.factories.OrderTestFactory;
import com.praktiker.shop.utilis.factories.UserTestFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderStatisticsTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderStatistics orderStatistics;

    @DisplayName("Testing of getAverageOrderSum() - positive case")
    @Test
    void shouldCalculateAverageOrderSum() {
        User user = UserTestFactory.createUser();

        List<Order> orders = OrderTestFactory.createOrdersList(user);

        when(orderRepository.findAll()).thenReturn(orders);

        BigDecimal avg = orderStatistics.getAverageOrderSum();

        assertEquals(BigDecimal.valueOf(14998.50).setScale(2), avg, "Average order sum calculated incorrectly!");
    }

    @DisplayName("Should throw OrderNotFoundException when no orders for average calculation")
    @Test
    void shouldThrowWhenNoOrdersForAverage() {
        when(orderRepository.findAll()).thenReturn(List.of());


        assertThrows(OrderNotFoundException.class, () -> orderStatistics.getAverageOrderSum(),
                     "Should throw OrderNotFoundException!");
    }

    @DisplayName("Should find the most expensive order")
    @Test
    void shouldFindMostExpensiveOrder() {
        List<OrderItem> orderItemsFirst = OrderItemTestFactory.createOrderItemList(1L, BigDecimal.ONE);
        Order cheapOrder = OrderTestFactory.createOrder(orderItemsFirst);

        List<OrderItem> orderItemsSecond = OrderItemTestFactory.createOrderItemList(2L, BigDecimal.valueOf(2));
        Order expensiveOrder = OrderTestFactory.createOrder(orderItemsSecond);

        when(orderRepository.findAll()).thenReturn(List.of(cheapOrder, expensiveOrder));

        Order found = orderStatistics.getMostExpensive();


        assertEquals(expensiveOrder, found, "Most expensive order not found correctly!");
    }

    @DisplayName("Should throw OrderNotFoundException when no orders for most expensive")
    @Test
    void shouldThrowWhenNoOrdersForMostExpensive() {
        when(orderRepository.findAll()).thenReturn(List.of());

        assertThrows(OrderNotFoundException.class, () -> orderStatistics.getMostExpensive(),
                     "Should throw OrderNotFoundException!");
    }

}

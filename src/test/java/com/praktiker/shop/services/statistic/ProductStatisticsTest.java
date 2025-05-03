package com.praktiker.shop.services.statistic;

import com.praktiker.shop.entities.order.Order;
import com.praktiker.shop.entities.order.OrderItem;
import com.praktiker.shop.entities.product.Product;
import com.praktiker.shop.exceptions.OrderNotFoundException;
import com.praktiker.shop.persistance.OrderRepository;
import com.praktiker.shop.services.statistics.ProductStatistic;
import com.praktiker.shop.utilis.factories.OrderItemTestFactory;
import com.praktiker.shop.utilis.factories.OrderTestFactory;
import com.praktiker.shop.utilis.factories.ProductTestFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductStatisticsTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private ProductStatistic productStatistic;

    @DisplayName("Testing getBestseller() - positive case")
    @Test
    void shouldFindBestseller() {
        List<Product> products = ProductTestFactory.createProductList();

        Product bestseller = products.getFirst();

        List<OrderItem> orderItemsFirst = OrderItemTestFactory.createOrderItemList(1L, BigDecimal.valueOf(5),
                                                                                   products.get(0));

        Order orderFirst = OrderTestFactory.createOrder(orderItemsFirst);

        List<OrderItem> orderItemsSecond = OrderItemTestFactory.createOrderItemList(2L, BigDecimal.ONE,
                                                                                    products.get(1));
        Order orderSecond = OrderTestFactory.createOrder(orderItemsSecond);

        when(orderRepository.findAll()).thenReturn(List.of(orderFirst, orderSecond));

        Product result = productStatistic.getBestseller();

        assertEquals(bestseller, result, "Bestseller was not found correctly!");
    }

    @DisplayName("Should throw OrderNotFoundException when no orders exist")
    @Test
    void shouldThrowWhenNoOrders() {
        when(orderRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(OrderNotFoundException.class, () -> productStatistic.getBestseller());
    }
}

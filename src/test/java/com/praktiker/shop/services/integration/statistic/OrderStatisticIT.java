package com.praktiker.shop.services.integration.statistic;

import com.praktiker.shop.entities.order.Order;
import com.praktiker.shop.persistance.OrderRepository;
import com.praktiker.shop.services.statistics.OrderStatistics;
import com.praktiker.shop.utilis.seeders.TestDataSeeder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Import(TestDataSeeder.class)
public class OrderStatisticIT {

    @Autowired
    private OrderStatistics orderStatistics;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TestDataSeeder testDataSeeder;

    @DisplayName("should calculate correct average order sum")
    @Test
    public void shouldCalculateAverageOrderSum() {
        testDataSeeder.initializeDatabase();
        List<Order> orders = orderRepository.findAll();

        BigDecimal expectedAverage = orders.stream()
                                           .map(Order::getTotalPrice)
                                           .reduce(BigDecimal.ZERO, BigDecimal::add)
                                           .divide(BigDecimal.valueOf(orders.size()), RoundingMode.HALF_UP);

        BigDecimal result = orderStatistics.getAverageOrderSum();

        assertThat(result).isEqualByComparingTo(expectedAverage);

    }
}

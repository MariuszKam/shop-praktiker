package com.praktiker.shop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.praktiker.shop.config.security.SecurityConfig;
import com.praktiker.shop.entities.order.Order;
import com.praktiker.shop.entities.product.Product;
import com.praktiker.shop.entities.user.User;
import com.praktiker.shop.services.statistics.OrderStatistics;
import com.praktiker.shop.services.statistics.ProductStatistic;
import com.praktiker.shop.utilis.factories.OrderTestFactory;
import com.praktiker.shop.utilis.factories.ProductTestFactory;
import com.praktiker.shop.utilis.factories.UserTestFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StatisticController.class)
@Import(SecurityConfig.class)
public class StatisticControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private OrderStatistics orderStatistics;

    @MockitoBean
    private ProductStatistic productStatistic;

    @Test
    public void shouldGetAverageOrderSum() throws Exception {
        BigDecimal averageOrderSum = BigDecimal.valueOf(150.50);

        when(orderStatistics.getAverageOrderSum()).thenReturn(averageOrderSum);

        mockMvc.perform(get("/statistics/order/average")
                                .with(csrf())
                                .with(user("admin").roles("ADMIN"))
                                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$").value(150.50));
    }

    @Test
    public void shouldGetMostExpensiveOrder() throws Exception {
        User user = UserTestFactory.createUser();
        Order order = OrderTestFactory.createOrder(user);
        order.setId(1L);

        when(orderStatistics.getMostExpensive()).thenReturn(order);

        mockMvc.perform(get("/statistics/order/most-expensive")
                                .with(csrf())
                                .with(user(user))
                                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1L))
               .andExpect(jsonPath("$.orderStatus").value("CREATED"))
               .andExpect(jsonPath("$.user.email").value("adam@mail.com"));
    }

    @Test
    public void shouldGetBestSellerProduct() throws Exception {
        Product product = ProductTestFactory.createProduct();
        product.setId(1L);

        when(productStatistic.getBestseller()).thenReturn(product);

        mockMvc.perform(get("/statistics/product/best-seller")
                                .with(csrf())
                                .with(user("admin").roles("ADMIN"))
                                .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1L))
               .andExpect(jsonPath("$.name").value("Default Product"))
               .andExpect(jsonPath("$.price").value(99.99));
    }
}

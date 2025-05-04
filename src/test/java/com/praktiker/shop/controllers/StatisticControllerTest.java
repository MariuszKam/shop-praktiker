package com.praktiker.shop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.praktiker.shop.config.security.SecurityConfig;
import com.praktiker.shop.dto.order.OrderResponse;
import com.praktiker.shop.dto.product.ProductResponse;
import com.praktiker.shop.entities.order.Order;
import com.praktiker.shop.entities.product.Product;
import com.praktiker.shop.mappers.OrderMapper;
import com.praktiker.shop.mappers.ProductMapper;
import com.praktiker.shop.services.statistics.OrderStatistics;
import com.praktiker.shop.services.statistics.ProductStatistic;
import com.praktiker.shop.utilis.factories.OrderTestFactory;
import com.praktiker.shop.utilis.factories.ProductTestFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        Order order = OrderTestFactory.createOrder();

        OrderResponse response = OrderMapper.toResponse(order);

        when(orderStatistics.getMostExpensive()).thenReturn(response);

        MvcResult mvcResult = mockMvc.perform(get("/statistics/order/most-expensive")
                                                      .with(csrf())
                                                      .with(user("admin").roles("ADMIN"))
                                                      .contentType(MediaType.APPLICATION_JSON))
                                     .andExpect(status().isOk())
                                     .andReturn();

        String json = mvcResult.getResponse().getContentAsString();

        OrderResponse actual = objectMapper.readValue(json, OrderResponse.class);

        assertEquals(response, actual, "Response is not correct!");
    }

    @Test
    public void shouldGetBestSellerProduct() throws Exception {
        Product product = ProductTestFactory.createProduct();

        ProductResponse response = ProductMapper.toResponse(product);

        when(productStatistic.getBestseller()).thenReturn(response);

        MvcResult mvcResult = mockMvc.perform(get("/statistics/product/best-seller")
                                                      .with(csrf())
                                                      .with(user("admin").roles("ADMIN"))
                                                      .contentType(MediaType.APPLICATION_JSON))
                                     .andExpect(status().isOk())
                                     .andReturn();

        String json = mvcResult.getResponse().getContentAsString();

        ProductResponse actual = objectMapper.readValue(json, ProductResponse.class);

        assertEquals(response, actual, "Response is not correct!");
    }
}

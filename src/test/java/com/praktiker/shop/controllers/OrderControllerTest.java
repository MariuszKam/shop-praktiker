package com.praktiker.shop.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.praktiker.shop.config.security.SecurityConfig;
import com.praktiker.shop.dto.order.OrderCreateRequest;
import com.praktiker.shop.dto.order.OrderResponse;
import com.praktiker.shop.dto.order.OrderStatusUpdateRequest;
import com.praktiker.shop.entities.order.Order;
import com.praktiker.shop.entities.order.OrderStatus;
import com.praktiker.shop.entities.user.User;
import com.praktiker.shop.services.OrderService;
import com.praktiker.shop.utilis.factories.OrderTestFactory;
import com.praktiker.shop.utilis.factories.UserTestFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@Import(SecurityConfig.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private OrderService orderService;

    @Test
    public void shouldGetOrderById() throws Exception {
        Order order = OrderTestFactory.createOrder();

        OrderResponse response = OrderTestFactory.createResponse(order);

        when(orderService.getOrderById(order.getId())).thenReturn(response);

        MvcResult mvcResult = mockMvc.perform(get(String.format("/order/%d", order.getId()))
                                                      .with(csrf())
                                                      .with(user(order.getUser()))
                                                      .contentType(MediaType.APPLICATION_JSON))
                                     .andExpect(status().isOk())
                                     .andReturn();

        String json = mvcResult.getResponse().getContentAsString();

        OrderResponse actual = objectMapper.readValue(json, OrderResponse.class);

        assertEquals(response, actual, "Response is not correct!");
    }

    @Test
    public void shouldGetOrdersByAuthenticatedUser() throws Exception {
        User user = UserTestFactory.createUser();

        List<Order> orders = OrderTestFactory.createOrders(user);

        List<OrderResponse> response = OrderTestFactory.createResponses(orders);

        when(orderService.getOrdersByUsername(user.getUsername())).thenReturn(response);

        MvcResult mvcResult = mockMvc.perform(get(String.format("/order/user/%s", user.getUsername()))
                                                      .with(csrf())
                                                      .with(user(user))
                                                      .contentType(MediaType.APPLICATION_JSON))
                                     .andExpect(status().isOk())
                                     .andReturn();

        String json = mvcResult.getResponse().getContentAsString();

        List<OrderResponse> actual = objectMapper.readValue(json, new TypeReference<>() {
        });

        assertEquals(response, actual, "Response is not correct!");
    }

    @Test
    public void shouldCreateOrder() throws Exception {
        User user = UserTestFactory.createUser();

        Order order = OrderTestFactory.createOrder(user);

        OrderCreateRequest request = OrderTestFactory.createRequest(order);

        OrderResponse response = OrderTestFactory.createResponse(order);

        when(orderService.createOrder(any(OrderCreateRequest.class), eq(user.getUsername())))
                .thenReturn(response);

        MvcResult mvcResult = mockMvc.perform(post("/order")
                                                      .with(csrf())
                                                      .with(user(user.getUsername()))
                                                      .contentType(MediaType.APPLICATION_JSON)
                                                      .content(objectMapper.writeValueAsString(request)))
                                     .andExpect(status().isCreated())
                                     .andReturn();

        String json = mvcResult.getResponse().getContentAsString();

        OrderResponse actual = objectMapper.readValue(json, OrderResponse.class);

        assertEquals(response, actual, "Response is not correct!");
    }

    @Test
    public void shouldUpdateStatus() throws Exception {
        User user = UserTestFactory.createUser();
        Order order = OrderTestFactory.createOrder(user);

        OrderStatusUpdateRequest request = new OrderStatusUpdateRequest(OrderStatus.PAID);

        OrderResponse response = OrderTestFactory.createResponse(order);

        when(orderService.changeOrderStatus(eq(order.getId()), any(OrderStatusUpdateRequest.class))).thenReturn(
                response);

        MvcResult mvcResult = mockMvc.perform(patch(String.format("/order/%d/status", order.getId()))
                                                      .with(csrf())
                                                      .with(user(user))
                                                      .contentType(MediaType.APPLICATION_JSON)
                                                      .content(objectMapper.writeValueAsString(request)))
                                     .andExpect(status().isOk())
                                     .andReturn();

        String json = mvcResult.getResponse().getContentAsString();

        OrderResponse actual = objectMapper.readValue(json, OrderResponse.class);

        assertEquals(response, actual, "Response is not correct!");
    }
}

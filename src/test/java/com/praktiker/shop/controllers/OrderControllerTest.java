package com.praktiker.shop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.praktiker.shop.config.security.SecurityConfig;
import com.praktiker.shop.entities.order.Order;
import com.praktiker.shop.entities.user.User;
import com.praktiker.shop.services.OrderService;
import com.praktiker.shop.utilis.ContentType;
import com.praktiker.shop.utilis.OrderTestFactory;
import com.praktiker.shop.utilis.UserTestFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
        User user = UserTestFactory.createUser();
        Order order = OrderTestFactory.createOrder(user);
        order.setId(1L);

        when(orderService.getOrderById(order.getId())).thenReturn(order);

        mockMvc.perform(get("/order/1")
                .with(csrf())
                .with(user(user))
                .contentType(ContentType.JSON.getName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.orderStatus").value("CREATED"))
                .andExpect(jsonPath("$.user.email").value("adam@mail.com"));
    }

    @Test
    public void shouldGetOrdersByUsername() throws Exception {
        User user = UserTestFactory.createUser();
        List<Order> orders = OrderTestFactory.createOrdersList(user);

        when(orderService.getOrdersByUsername(user.getUsername())).thenReturn(orders);

        mockMvc.perform(get("/order/user/Adam")
                .with(csrf())
                .with(user(user))
                .contentType(ContentType.JSON.getName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].user.email").value("adam@mail.com"))
                .andExpect(jsonPath("$[0].user.username").value("Adam"))
                .andExpect(jsonPath("$[1].user.email").value("adam@mail.com"))
                .andExpect(jsonPath("$[1].user.username").value("Adam"));
    }
}

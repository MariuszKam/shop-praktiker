package com.praktiker.shop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.praktiker.shop.config.security.SecurityConfig;
import com.praktiker.shop.entities.product.Product;
import com.praktiker.shop.entities.user.User;
import com.praktiker.shop.services.ProductService;
import com.praktiker.shop.utilis.factories.ProductTestFactory;
import com.praktiker.shop.utilis.factories.UserTestFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@Import(SecurityConfig.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductService productService;

    @Test
    public void shouldGetProductById() throws Exception {
        Product product = ProductTestFactory.createProduct();
        product.setId(1L);

        User user = UserTestFactory.createUser();

        when(productService.getProductById(product.getId())).thenReturn(product);

        mockMvc.perform(get("/products/1")
                                .with(csrf())
                                .with(user(user))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(product)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1L))
               .andExpect(jsonPath("$.name").value("Default Product"))
               .andExpect(jsonPath("$.price").value("99.99"))
               .andExpect(jsonPath("$.productType").value("ELECTRONICS"));
    }
}

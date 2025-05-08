package com.praktiker.shop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.praktiker.shop.config.security.SecurityConfig;
import com.praktiker.shop.controllers.product.ProductController;
import com.praktiker.shop.dto.product.ProductResponse;
import com.praktiker.shop.entities.product.Product;
import com.praktiker.shop.mappers.ProductMapper;
import com.praktiker.shop.services.product.ProductService;
import com.praktiker.shop.utilis.factories.ProductTestFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

        ProductResponse response = ProductMapper.toResponse(product);

        when(productService.getProductById(product.getId())).thenReturn(response);

        MvcResult mvcResult = mockMvc.perform(get(String.format("/products/%d", product.getId()))
                                                      .with(csrf())
                                                      .contentType(MediaType.APPLICATION_JSON))
                                     .andExpect(status().isOk())
                                     .andReturn();

        String json = mvcResult.getResponse().getContentAsString();

        ProductResponse actual = objectMapper.readValue(json, ProductResponse.class);

        assertEquals(response, actual, "Response is not correct!");
    }
}

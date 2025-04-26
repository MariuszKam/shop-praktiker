package com.praktiker.shop.services;

import com.praktiker.shop.entities.product.Product;
import com.praktiker.shop.exceptions.ProductNotFoundException;
import com.praktiker.shop.persistance.ProductRepository;
import com.praktiker.shop.utilis.ProductTestFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @DisplayName("Part of testing getProductById(Long id) - positive case")
    @Test
    public void shouldGetProductById() {
        Product product = ProductTestFactory.createProduct();
        product.setId(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product actual = productService.getProductById(1L);

        assertEquals(product.getId(), actual.getId(), "Product wrong ID!");
        assertEquals(product.getName(), actual.getName(), "Product wrong name!");
        assertEquals(product.getPrice(), actual.getPrice(), "Product wrong price!");
        assertEquals(product.getProductType(), actual.getProductType(), "Product wrong Type!");
    }

    @DisplayName("Part of testing getProductById(Long id) - negative case")
    @Test
    public void shouldThrowProductNotFoundExceptionById() {
        Long notExist = 2L;

        when(productRepository.findById(notExist)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(notExist),
                "Should throw ProductNotFoundException for non existing ID");
    }
}

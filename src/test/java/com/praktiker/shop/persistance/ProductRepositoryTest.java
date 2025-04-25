package com.praktiker.shop.persistance;

import com.praktiker.shop.entities.product.Product;
import com.praktiker.shop.entities.product.ProductType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void shouldFindProductByName() {
        Product product = new Product();
        product.setName("How to code");
        product.setPrice(new BigDecimal("26.99"));
        product.setProductType(ProductType.BOOK);

        testEntityManager.persist(product);
        testEntityManager.flush();

        Optional<Product> found = productRepository.findByName("How to code");
        assertTrue(found.isPresent(), "Product is not present!");
        Product actual = found.get();
        assertEquals("How to code", actual.getName(), "Product name is different!");
        assertEquals(new BigDecimal("26.99"), actual.getPrice(), "Product price is different!");
        assertEquals(ProductType.BOOK, actual.getProductType(), "Product type is different!");
    }
}

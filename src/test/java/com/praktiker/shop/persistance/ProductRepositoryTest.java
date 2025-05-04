package com.praktiker.shop.persistance;

import com.praktiker.shop.entities.product.Product;
import com.praktiker.shop.entities.product.ProductType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void shouldFindProductByName() {
        Product product = Product.builder()
                                 .name("How to code")
                                 .price(new BigDecimal("26.99"))
                                 .productType(ProductType.BOOK)
                                 .build();

        testEntityManager.persist(product);
        testEntityManager.flush();

        Product found = productRepository.findByName("How to code")
                                         .orElseThrow(() -> new AssertionError("Product not found"));

        assertThat(found.getName()).isEqualTo("How to code");
        assertThat(found.getPrice()).isEqualByComparingTo("26.99");
        assertThat(found.getProductType()).isEqualTo(ProductType.BOOK);
    }

    @Test
    @DisplayName("should return empty when product name not found")
    void shouldReturnEmptyWhenProductNotFound() {
        Optional<Product> result = productRepository.findByName("Nonexistent Product");
        assertThat(result).isEmpty();
    }
}

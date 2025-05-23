package com.praktiker.shop.utilis.factories;

import com.praktiker.shop.entities.product.Product;
import com.praktiker.shop.entities.product.ProductType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductTestFactory {

    public static Product createProduct(Long id, String name, BigDecimal price, ProductType type) {
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setPrice(price);
        product.setProductType(type);
        product.setOrderItems(new ArrayList<>());
        return product;
    }

    public static Product createProductForRepo(String name, BigDecimal price, ProductType type) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setProductType(type);
        product.setOrderItems(new ArrayList<>());
        return product;
    }

    public static Product createProduct() {
        return createProduct(1L, "Default Product", BigDecimal.valueOf(99.99), ProductType.ELECTRONICS);
    }

    public static List<Product> createProductList() {
        return List.of(createProduct(1L, "Laptop", BigDecimal.valueOf(2999.99), ProductType.ELECTRONICS),
                       createProduct(2L, "T-shirt", BigDecimal.valueOf(49.99), ProductType.CLOTH),
                       createProduct(3L, "Book", BigDecimal.valueOf(29.99), ProductType.BOOK));
    }

    public static List<Product> createProductsForRepo() {
        return List.of(
                createProductForRepo("Laptop", BigDecimal.valueOf(2999.99), ProductType.ELECTRONICS),
                createProductForRepo("T-shirt", BigDecimal.valueOf(49.99), ProductType.CLOTH),
                createProductForRepo("Book", BigDecimal.valueOf(29.99), ProductType.BOOK)
        );
    }
}

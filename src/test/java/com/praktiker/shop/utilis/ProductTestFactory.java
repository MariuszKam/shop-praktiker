package com.praktiker.shop.utilis;

import com.praktiker.shop.entities.product.Product;
import com.praktiker.shop.entities.product.ProductType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductTestFactory {

    public static Product createProduct(String name, BigDecimal price, ProductType type) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setProductType(type);
        product.setOrderItems(new ArrayList<>());
        return product;
    }

    public static Product createProduct() {
        return createProduct("Default Product", BigDecimal.valueOf(99.99), ProductType.ELECTRONICS);
    }

    public static List<Product> createProductList() {
        List<Product> products = new ArrayList<>();
        products.add(createProduct("Laptop", BigDecimal.valueOf(2999.99), ProductType.ELECTRONICS));
        products.add(createProduct("T-shirt", BigDecimal.valueOf(49.99), ProductType.CLOTH));
        products.add(createProduct("Book", BigDecimal.valueOf(29.99), ProductType.BOOK));
        return products;
    }
}

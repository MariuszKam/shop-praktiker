package com.praktiker.shop.entities.product;

import java.util.ArrayList;
import java.util.List;

public class Warehouse {

    private static class WarehouseHolder {
        private static final Warehouse INSTANCE = new Warehouse();
    }

    private List<Product> products;

    private Warehouse() {
        this.products = new ArrayList<>();
    }

    public static Warehouse getInstance() {
        return WarehouseHolder.INSTANCE;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public List<Product> getProducts() {
        return products;
    }
}

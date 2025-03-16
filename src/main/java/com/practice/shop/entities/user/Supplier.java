package com.practice.shop.entities.user;

import com.practice.shop.entities.product.Product;

import java.util.List;

public class Supplier extends User {

    private List<Product> products;

    public Supplier(int id, String name, String email) {
        super(id, name, email);
    }
}

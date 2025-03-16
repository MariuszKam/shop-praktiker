package com.practice.shop.entities.user;

import com.practice.shop.entities.product.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Supplier extends User {

    private List<Product> products;

    public Supplier(int id, String name, String email) {
        super(id, name, email);
    }
}

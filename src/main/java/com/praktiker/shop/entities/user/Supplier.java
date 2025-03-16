package com.praktiker.shop.entities.user;

import com.praktiker.shop.entities.product.Product;
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

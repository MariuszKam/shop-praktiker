package com.praktiker.shop.entities.user;

import com.praktiker.shop.entities.order.Order;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class Customer extends User {

    private List<Order> orders;
    private int loyaltyPoints;

    public Customer(int id, String name, String email) {
        super(id, name, email);
    }
}

package com.practice.shop.entities.order;

import com.practice.shop.entities.product.Product;
import com.practice.shop.entities.user.Customer;

import java.util.List;

public class Order {

    private int id;
    private List<Product> products;
    private OrderStatus orderStatus;
    private Customer customer;
    private Payment payment;
}

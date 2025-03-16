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

    public Order(int id, List<Product> products, OrderStatus orderStatus, Customer customer, Payment payment) {
        this.id = id;
        this.products = products;
        this.orderStatus = orderStatus;
        this.customer = customer;
        this.payment = payment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}

package com.praktiker.shop.entities.order;

import java.time.LocalDate;

public class PayPalPayment extends Payment{

    private String email;

    public PayPalPayment(int id, float amount, LocalDate date, String email) {
        super(id, amount, date);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

package com.practice.shop.entities.order;

import java.time.LocalDate;

public class CreditCardPayment extends Payment {

    private int cardNumber;

    public CreditCardPayment(int id, float amount, LocalDate date, int cardNumber) {
        super(id, amount, date);
        this.cardNumber = cardNumber;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }
}

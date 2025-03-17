package com.praktiker.shop.entities.order;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    private int id;
    private float amount;
    private LocalDate date;
    private PaymentMethod paymentMethod;

}

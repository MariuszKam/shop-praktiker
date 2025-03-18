package com.praktiker.shop.persistance;

import com.praktiker.shop.entities.order.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}

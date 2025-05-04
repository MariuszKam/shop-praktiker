package com.praktiker.shop.utilis.factories;

import com.praktiker.shop.entities.order.Payment;
import com.praktiker.shop.entities.order.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

public class PaymentTestFactory {

    public static Payment createPaymentForRepo(BigDecimal amount, PaymentMethod paymentMethod) {
        Payment payment = new Payment();
        payment.setAmount(amount);
        payment.setPaymentMethod(paymentMethod);
        return payment;
    }

    public static List<Payment> createPaymentsForRepo() {
        return List.of(createPaymentForRepo(BigDecimal.ZERO, PaymentMethod.PAYPAL),
                       createPaymentForRepo(BigDecimal.ZERO, PaymentMethod.CREDIT_CARD),
                       createPaymentForRepo(BigDecimal.ZERO, PaymentMethod.PAYPAL));
    }
}

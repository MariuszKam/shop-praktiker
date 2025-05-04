package com.praktiker.shop.persistance;

import com.praktiker.shop.entities.order.Order;
import com.praktiker.shop.entities.order.OrderItem;
import com.praktiker.shop.entities.order.Payment;
import com.praktiker.shop.entities.order.PaymentMethod;
import com.praktiker.shop.entities.product.Product;
import com.praktiker.shop.entities.user.Role;
import com.praktiker.shop.entities.user.User;
import com.praktiker.shop.utilis.factories.OrderItemTestFactory;
import com.praktiker.shop.utilis.factories.OrderTestFactory;
import com.praktiker.shop.utilis.factories.ProductTestFactory;
import com.praktiker.shop.utilis.factories.UserTestFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void shouldFindAllOrdersByUsername() {
        Role role = new Role();
        role.setName("ROLE_USER");

        testEntityManager.persist(role);

        User user = UserTestFactory.createUser();
        user.getRoles().add(role);
        testEntityManager.persist(user);

        List<Product> products = ProductTestFactory.createProductsForRepo();
        products.forEach(product -> testEntityManager.persist(product));

        List<OrderItem> orderItems = OrderItemTestFactory.createOrderItemsForRepo(products);
        orderItems.forEach(orderItem -> testEntityManager.persist(orderItem));

        Payment payment = new Payment();
        payment.setAmount(BigDecimal.ZERO);
        payment.setPaymentMethod(PaymentMethod.PAYPAL);

        testEntityManager.persist(payment);

        List<Order> orders = OrderTestFactory.createOrdersForRepo(orderItems, user, payment);
        orders.forEach(order -> testEntityManager.persist(order));

        testEntityManager.flush();

        List<Order> found = orderRepository.findAllByUser_Username("Adam");

        assertThat(found)
                .isNotEmpty()
                .hasSize(orders.size())
                .allMatch(order -> order.getUser().getUsername().equals("Adam"));

        assertThat(found).allSatisfy(order -> assertThat(order.getOrderItems())
                .isNotEmpty()
                .allSatisfy(item -> {
                    assertThat(item.getProduct()).isNotNull();
                    assertThat(item.getQuantity()).isGreaterThan(BigDecimal.ZERO);
                }));

        assertThat(found)
                .extracting(Order::getPayment)
                .allSatisfy(p -> {
                    assertThat(p.getPaymentMethod()).isEqualTo(PaymentMethod.PAYPAL);
                    assertThat(p.getAmount()).isEqualByComparingTo(BigDecimal.ZERO);
                });
    }
}

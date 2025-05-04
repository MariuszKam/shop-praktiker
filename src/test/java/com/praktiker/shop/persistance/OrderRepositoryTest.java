package com.praktiker.shop.persistance;

import com.praktiker.shop.entities.order.Order;
import com.praktiker.shop.entities.order.OrderItem;
import com.praktiker.shop.entities.order.Payment;
import com.praktiker.shop.entities.product.Product;
import com.praktiker.shop.entities.user.Role;
import com.praktiker.shop.entities.user.User;
import com.praktiker.shop.utilis.factories.*;
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

        List<List<OrderItem>> orderItems = OrderItemTestFactory.createOrderItemsListsForRepo(products);
        orderItems.stream()
                  .flatMap(List::stream)
                  .forEach(testEntityManager::persist);

        List<Payment> payments = PaymentTestFactory.createPaymentsForRepo();

        payments.forEach(payment -> testEntityManager.persist(payment));

        List<Order> orders = OrderTestFactory.createOrdersForRepo(orderItems, user, payments);
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
                .containsExactlyElementsOf(
                        orders.stream()
                              .map(Order::getPayment)
                              .toList()
                );
    }
}

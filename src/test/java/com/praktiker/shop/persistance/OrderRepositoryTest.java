package com.praktiker.shop.persistance;

import com.praktiker.shop.entities.order.Order;
import com.praktiker.shop.entities.user.User;
import com.praktiker.shop.utilis.OrderTestFactory;
import com.praktiker.shop.utilis.UserTestFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void shouldFindAllOrdersByUsername() {
        User user = UserTestFactory.createUser();
        testEntityManager.persist(user);

        List<Order> orders = OrderTestFactory.createOrdersForUser(user);
        orders.forEach(order -> testEntityManager.persist(order));
        testEntityManager.flush();

        List<Order> found = orderRepository.findAllByUser_Username("Adam");
        assertFalse(found.isEmpty(), "List of orders is empty!");
        assertThat(found).allMatch(order -> order.getUser().getUsername().equals("Adam"));
    }
}

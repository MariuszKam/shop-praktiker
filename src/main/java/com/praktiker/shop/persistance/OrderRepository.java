package com.praktiker.shop.persistance;

import com.praktiker.shop.entities.order.Order;
import com.praktiker.shop.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);
}

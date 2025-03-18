package com.praktiker.shop.persistance;

import com.praktiker.shop.entities.order.Order;
import com.praktiker.shop.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);
}

package com.praktiker.shop.entities.order;

import com.praktiker.shop.entities.product.Product;
import com.praktiker.shop.entities.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private List<Product> products;
    private OrderStatus orderStatus;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private Payment payment;

}

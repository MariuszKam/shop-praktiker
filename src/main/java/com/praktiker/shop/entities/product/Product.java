package com.praktiker.shop.entities.product;

import com.praktiker.shop.entities.order.OrderItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private ProductType productType;
    @OneToMany(mappedBy = "product")
    @JoinColumn(name = "product_id")
    private OrderItem orderItem;

}

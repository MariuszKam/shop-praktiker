package com.praktiker.shop.entities.product.stock;

import com.praktiker.shop.entities.product.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "product_stock")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductStock {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    public void addAmount(BigDecimal amount) {
        this.amount = this.amount.add(amount);
    }

    public void subtractAmount(BigDecimal amount) {
        this.amount = this.amount.subtract(amount);
    }
}

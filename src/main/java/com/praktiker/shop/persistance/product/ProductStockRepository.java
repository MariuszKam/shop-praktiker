package com.praktiker.shop.persistance.product;

import com.praktiker.shop.entities.product.stock.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {
}

package com.praktiker.shop.mappers;

import com.praktiker.shop.dto.product.ProductCreateRequest;
import com.praktiker.shop.entities.product.Product;
import com.praktiker.shop.entities.product.ProductStock;

public class ProductStockMapper {

    public static ProductStock toEntity(Product product, ProductCreateRequest request) {
        return ProductStock.builder().product(product).amount(request.getAmount()).build();
    }
}

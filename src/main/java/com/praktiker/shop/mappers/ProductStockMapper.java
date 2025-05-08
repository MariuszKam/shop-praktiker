package com.praktiker.shop.mappers;

import com.praktiker.shop.dto.product.ProductCreateRequest;
import com.praktiker.shop.dto.product.stock.ProductStockResponse;
import com.praktiker.shop.entities.product.Product;
import com.praktiker.shop.entities.product.stock.ProductStock;

import java.util.List;

public class ProductStockMapper {

    public static ProductStock toEntity(Product product, ProductCreateRequest request) {
        return ProductStock.builder().product(product).amount(request.getAmount()).build();
    }

    public static ProductStockResponse toResponse(ProductStock productStock) {
        return new ProductStockResponse(productStock.getProduct().getName(),
                                        productStock.getProduct().getUnit(),
                                        productStock.getProduct().getProductType().getName(),
                                        productStock.getAmount());
    }

    public static List<ProductStockResponse> toResponse(List<ProductStock> productStocks) {
        return productStocks.stream().map(ProductStockMapper::toResponse).toList();
    }
}

package com.praktiker.shop.mappers;

import com.praktiker.shop.dto.product.ProductCreateRequest;
import com.praktiker.shop.dto.product.ProductResponse;
import com.praktiker.shop.entities.product.Product;
import com.praktiker.shop.entities.product.ProductType;

public class ProductMapper {

    public static Product toEntity(ProductCreateRequest request, ProductType productType) {
        return Product.builder()
                      .name(request.getName())
                      .price(request.getPrice())
                      .unit(request.getUnit())
                      .productType(productType)
                      .build();
    }

    public static ProductResponse toResponse(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(),
                                   product.getUnit().name(),
                                   product.getProductType().getName());
    }

}

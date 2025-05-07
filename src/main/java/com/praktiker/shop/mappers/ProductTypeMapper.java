package com.praktiker.shop.mappers;

import com.praktiker.shop.dto.product.ProductTypeCreateRequest;
import com.praktiker.shop.dto.product.ProductTypeResponse;
import com.praktiker.shop.entities.product.ProductType;

public class ProductTypeMapper {

    public static ProductType toEntity(ProductTypeCreateRequest request) {
        ProductType productType = new ProductType();
        productType.setName(request.getName());
        return productType;
    }

    public static ProductTypeResponse toResponse(ProductType productType) {
        return new ProductTypeResponse(productType.getId(), productType.getName());
    }
}

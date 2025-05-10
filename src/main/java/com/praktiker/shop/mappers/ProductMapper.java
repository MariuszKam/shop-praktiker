package com.praktiker.shop.mappers;

import com.praktiker.shop.dto.product.ProductCreateRequest;
import com.praktiker.shop.dto.product.ProductResponse;
import com.praktiker.shop.entities.product.Product;
import com.praktiker.shop.entities.product.ProductType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "productType", source = "productType")
    @Mapping(target = "stock", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    Product toEntity(ProductCreateRequest request, ProductType productType);

    @Mapping(target = "productType", source = "productType.name")
    ProductResponse toResponse(Product product);
}
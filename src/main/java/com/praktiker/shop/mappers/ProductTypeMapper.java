package com.praktiker.shop.mappers;

import com.praktiker.shop.dto.product.ProductTypeCreateRequest;
import com.praktiker.shop.dto.product.ProductTypeResponse;
import com.praktiker.shop.entities.product.ProductType;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductTypeMapper {

    ProductType toEntity(ProductTypeCreateRequest request);

    ProductTypeResponse toResponse(ProductType entity);

    List<ProductTypeResponse> toResponse(List<ProductType> entities);
}

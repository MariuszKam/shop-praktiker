package com.praktiker.shop.mappers;

import com.praktiker.shop.dto.product.ProductCreateRequest;
import com.praktiker.shop.dto.product.stock.ProductStockResponse;
import com.praktiker.shop.entities.product.Product;
import com.praktiker.shop.entities.product.stock.ProductStock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductStockMapper {

    ProductStock toEntity(Product product, ProductCreateRequest request);

    @Mapping(target = "name", source = "product.name")
    @Mapping(target = "unit", source = "product.unit")
    @Mapping(target = "productType", source = "product.productType.name")
    ProductStockResponse toResponse(ProductStock productStock);

    List<ProductStockResponse> toResponse(List<ProductStock> productStocks);
}

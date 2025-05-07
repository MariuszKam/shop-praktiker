package com.praktiker.shop.services;

import com.praktiker.shop.dto.product.ProductTypeCreateRequest;
import com.praktiker.shop.dto.product.ProductTypeResponse;
import com.praktiker.shop.entities.product.ProductType;
import com.praktiker.shop.exceptions.ProductTypeNotFoundException;
import com.praktiker.shop.mappers.ProductTypeMapper;
import com.praktiker.shop.persistance.ProductTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductTypeService {

    private final ProductTypeRepository productTypeRepository;

    public ProductTypeResponse getProductType(Long id) {
        ProductType productType = productTypeRepository.findById(id)
                                                       .orElseThrow(() -> new ProductTypeNotFoundException(
                                                               "Product Type not found"));

        return ProductTypeMapper.toResponse(productType);
    }

    public List<ProductTypeResponse> getAllProductType() {
        List<ProductType> productTypes = productTypeRepository.findAll();

        return ProductTypeMapper.toResponse(productTypes);
    }

    public ProductTypeResponse addProductType(ProductTypeCreateRequest request) {
        ProductType productType = ProductTypeMapper.toEntity(request);

        productTypeRepository.save(productType);

        return ProductTypeMapper.toResponse(productType);
    }
}

package com.praktiker.shop.services.product;

import com.praktiker.shop.dto.product.ProductTypeCreateRequest;
import com.praktiker.shop.dto.product.ProductTypeResponse;
import com.praktiker.shop.entities.product.ProductType;
import com.praktiker.shop.exceptions.ProductTypeNotFoundException;
import com.praktiker.shop.mappers.ProductTypeMapper;
import com.praktiker.shop.persistance.product.ProductTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductTypeService {

    private final ProductTypeRepository productTypeRepository;

    private final ProductTypeMapper productTypeMapper;

    public ProductTypeResponse getProductType(Long id) {
        ProductType productType = productTypeRepository.findById(id)
                                                       .orElseThrow(() -> new ProductTypeNotFoundException(
                                                               "Product Type not found"));

        return productTypeMapper.toResponse(productType);
    }

    public List<ProductTypeResponse> getAllProductType() {
        List<ProductType> productTypes = productTypeRepository.findAll();

        return productTypeMapper.toResponse(productTypes);
    }

    public ProductTypeResponse addProductType(ProductTypeCreateRequest request) {
        ProductType productType = productTypeMapper.toEntity(request);

        productTypeRepository.save(productType);

        return productTypeMapper.toResponse(productType);
    }

    public ProductTypeResponse updateProductType(Long typeId, ProductTypeCreateRequest request) {
        ProductType productType = productTypeRepository.findById(typeId)
                                                       .orElseThrow(() -> new ProductTypeNotFoundException(
                                                               "Product Type not found"
                                                       ));
        productType.setName(request.getName());
        productTypeRepository.save(productType);

        return productTypeMapper.toResponse(productType);
    }
}

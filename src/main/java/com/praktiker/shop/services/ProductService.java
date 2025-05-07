package com.praktiker.shop.services;

import com.praktiker.shop.dto.product.ProductCreateRequest;
import com.praktiker.shop.dto.product.ProductResponse;
import com.praktiker.shop.entities.product.Product;
import com.praktiker.shop.entities.product.ProductStock;
import com.praktiker.shop.entities.product.ProductType;
import com.praktiker.shop.exceptions.ProductNotFoundException;
import com.praktiker.shop.exceptions.ProductTypeNotFoundException;
import com.praktiker.shop.mappers.ProductMapper;
import com.praktiker.shop.mappers.ProductStockMapper;
import com.praktiker.shop.persistance.ProductRepository;
import com.praktiker.shop.persistance.ProductStockRepository;
import com.praktiker.shop.persistance.ProductTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductStockRepository productStockRepository;

    private final ProductTypeRepository productTypeRepository;

    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                                           .orElseThrow(() -> new ProductNotFoundException(
                                                   "Product with id: " + id + "does not exists"));

        return ProductMapper.toResponse(product);
    }

    public ProductResponse addProduct(ProductCreateRequest request) {
        ProductType productType = productTypeRepository.findById(request.getProductTypeId())
                                                       .orElseThrow(() -> new ProductTypeNotFoundException(
                                                               "Product Type id: " + request.getProductTypeId() + "does not exists"));

        Product product = ProductMapper.toEntity(request, productType);

        ProductStock productStock = ProductStockMapper.toEntity(product, request);

        product.setStock(productStock);

        productRepository.save(product);

        return ProductMapper.toResponse(product);
    }
}

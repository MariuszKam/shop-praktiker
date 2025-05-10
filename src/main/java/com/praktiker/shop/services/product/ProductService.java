package com.praktiker.shop.services.product;

import com.praktiker.shop.dto.product.ProductCreateRequest;
import com.praktiker.shop.dto.product.ProductResponse;
import com.praktiker.shop.entities.product.Product;
import com.praktiker.shop.entities.product.stock.ProductStock;
import com.praktiker.shop.entities.product.ProductType;
import com.praktiker.shop.exceptions.ProductNotFoundException;
import com.praktiker.shop.exceptions.ProductTypeNotFoundException;
import com.praktiker.shop.mappers.ProductMapper;
import com.praktiker.shop.mappers.ProductStockMapper;
import com.praktiker.shop.persistance.product.ProductRepository;
import com.praktiker.shop.persistance.product.ProductStockRepository;
import com.praktiker.shop.persistance.product.ProductTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductTypeRepository productTypeRepository;

    private final ProductStockRepository productStockRepository;

    private final ProductMapper productMapper;

    private final ProductStockMapper productStockMapper;

    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                                           .orElseThrow(() -> new ProductNotFoundException(
                                                   "Product with id: " + id + " does not exists"
                                           ));

        return productMapper.toResponse(product);
    }

    public ProductResponse createProduct(ProductCreateRequest request) {
        ProductType productType = productTypeRepository.findById(request.getProductTypeId())
                                                       .orElseThrow(() -> new ProductTypeNotFoundException(
                                                               "Product Type id: " + request.getProductTypeId() + " does not exists"
                                                       ));

        Product product = productMapper.toEntity(request, productType);
        productRepository.save(product);

        ProductStock productStock = productStockMapper.toEntity(product, request);
        productStockRepository.save(productStock);

        return productMapper.toResponse(product);
    }

    @Transactional
    public ProductResponse updateProduct(Long productId, ProductCreateRequest request) {
        Product product = productRepository.findById(productId)
                                           .orElseThrow(() -> new ProductNotFoundException(
                                                   "Product with id: " + productId + " does not exists"
                                           ));

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setUnit(request.getUnit());
        ProductType productType = productTypeRepository.findById(request.getProductTypeId())
                                                       .orElseThrow(() -> new ProductTypeNotFoundException(
                                                               "Product Type id: " + request.getProductTypeId() + " does not exists"
                                                       ));

        product.setProductType(productType);

        return productMapper.toResponse(product);
    }

    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                                           .orElseThrow(() -> new ProductNotFoundException(
                                                   "Product with id: " + productId + " does not exists"
                                           ));

        productStockRepository.findById(productId).ifPresent(productStockRepository::delete);

        productRepository.delete(product);
    }
}

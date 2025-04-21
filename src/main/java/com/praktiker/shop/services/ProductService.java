package com.praktiker.shop.services;

import com.praktiker.shop.entities.product.Product;
import com.praktiker.shop.exceptions.ProductNotFoundException;
import com.praktiker.shop.persistance.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() ->
                new ProductNotFoundException("Product wiith id: " + id + "does not exists"));
    }
}

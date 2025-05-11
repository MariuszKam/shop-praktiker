package com.praktiker.shop.services.product;

import com.praktiker.shop.dto.product.stock.ProductStockRequest;
import com.praktiker.shop.dto.product.stock.ProductStockResponse;
import com.praktiker.shop.entities.product.stock.ProductStock;
import com.praktiker.shop.exceptions.product.stock.ProductStockNotFoundException;
import com.praktiker.shop.mappers.product.ProductStockMapper;
import com.praktiker.shop.persistance.product.ProductStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductStockService {

    private final ProductStockRepository productStockRepository;

    private final ProductStockMapper productStockMapper;

    public ProductStockResponse getProductStock(Long id) {
        ProductStock productStock = productStockRepository.findById(id)
                                                          .orElseThrow(() -> new ProductStockNotFoundException(
                                                                  "Product Stock not found"
                                                          ));

        return productStockMapper.toResponse(productStock);
    }

    public List<ProductStockResponse> getAllProductStock() {
        List<ProductStock> productStocks = productStockRepository.findAll();

        return productStockMapper.toResponse(productStocks);
    }

    public ProductStockResponse addAmountToStock(ProductStockRequest request) {
        ProductStock productStock = productStockRepository.findById(request.getProductId())
                                                          .orElseThrow(() -> new ProductStockNotFoundException(
                                                                  "Product Stock not found"
                                                          ));
        productStock.addAmount(request.getAmount());

        productStockRepository.save(productStock);

        return productStockMapper.toResponse(productStock);
    }

}

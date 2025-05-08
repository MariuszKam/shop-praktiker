package com.praktiker.shop.services.product;

import com.praktiker.shop.dto.product.stock.ProductStockRequest;
import com.praktiker.shop.dto.product.stock.ProductStockResponse;
import com.praktiker.shop.entities.product.stock.ProductStock;
import com.praktiker.shop.exceptions.ProductStockNotFoundException;
import com.praktiker.shop.mappers.ProductStockMapper;
import com.praktiker.shop.persistance.product.ProductStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductStockService {

    private final ProductStockRepository productStockRepository;

    public ProductStockResponse getProductStock(Long id) {
        ProductStock productStock = productStockRepository.findById(id)
                                                          .orElseThrow(() -> new ProductStockNotFoundException(
                                                                  "Product Stock not found"
                                                          ));

        return ProductStockMapper.toResponse(productStock);
    }

    public List<ProductStockResponse> getAllProductStock() {
        List<ProductStock> productStocks = productStockRepository.findAll();

        return ProductStockMapper.toResponse(productStocks);
    }

    public ProductStockResponse addAmountToStock(ProductStockRequest request) {
        ProductStock productStock = productStockRepository.findById(request.getProductId())
                                                          .orElseThrow(() -> new ProductStockNotFoundException(
                                                                  "Product Stock not found"
                                                          ));
        productStock.addAmount(request.getAmount());

        productStockRepository.save(productStock);

        return ProductStockMapper.toResponse(productStock);
    }

}

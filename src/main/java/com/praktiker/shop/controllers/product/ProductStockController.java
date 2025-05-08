package com.praktiker.shop.controllers.product;

import com.praktiker.shop.dto.product.stock.ProductStockRequest;
import com.praktiker.shop.dto.product.stock.ProductStockResponse;
import com.praktiker.shop.services.product.ProductStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products/stock")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class ProductStockController {

    private final ProductStockService productStockService;

    @GetMapping("/{productId}")
    public ResponseEntity<ProductStockResponse> getProductStock(@PathVariable Long id) {
        return ResponseEntity.ok(productStockService.getProductStock(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductStockResponse>> getAllProductStock() {
        return ResponseEntity.ok(productStockService.getAllProductStock());
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ProductStockResponse> addProductStock(
            @PathVariable Long productId,
            @RequestBody @Valid ProductStockRequest request
    ) {
        if (!productId.equals(request.getProductId())) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(productStockService.addAmountToStock(request));
    }
}

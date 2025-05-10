package com.praktiker.shop.controllers.product;

import com.praktiker.shop.dto.product.ProductCreateRequest;
import com.praktiker.shop.dto.product.ProductResponse;
import com.praktiker.shop.services.product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponse> addProduct(@RequestBody @Valid ProductCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.addProduct(request));
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}

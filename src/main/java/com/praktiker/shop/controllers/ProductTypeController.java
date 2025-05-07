package com.praktiker.shop.controllers;

import com.praktiker.shop.dto.product.ProductTypeCreateRequest;
import com.praktiker.shop.dto.product.ProductTypeResponse;
import com.praktiker.shop.services.ProductTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@RestController
@Repository("/product-type")
@RequiredArgsConstructor
public class ProductTypeController {

    private final ProductTypeService productTypeService;

    @GetMapping("/{typeId}")
    public ResponseEntity<ProductTypeResponse> getProductType(@PathVariable Long id) {
        return ResponseEntity.ok(productTypeService.getProductType(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProductTypeResponse> addProductType(@RequestBody ProductTypeCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productTypeService.addProductType(request));
    }
}

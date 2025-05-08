package com.praktiker.shop.controllers.product;

import com.praktiker.shop.dto.product.ProductTypeCreateRequest;
import com.praktiker.shop.dto.product.ProductTypeResponse;
import com.praktiker.shop.services.product.ProductTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Repository("/products/type")
@RequiredArgsConstructor
public class ProductTypeController {

    private final ProductTypeService productTypeService;

    @GetMapping("/{typeId}")
    public ResponseEntity<ProductTypeResponse> getProductType(@PathVariable Long id) {
        return ResponseEntity.ok(productTypeService.getProductType(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductTypeResponse>> getAllProductType() {
        return ResponseEntity.ok(productTypeService.getAllProductType());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProductTypeResponse> addProductType(@RequestBody ProductTypeCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productTypeService.addProductType(request));
    }
}

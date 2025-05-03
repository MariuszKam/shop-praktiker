package com.praktiker.shop.controllers;

import com.praktiker.shop.dto.order.OrderCreateRequest;
import com.praktiker.shop.dto.order.OrderResponse;
import com.praktiker.shop.dto.order.OrderStatusUpdateRequest;
import com.praktiker.shop.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @PreAuthorize("#username == authentication.name")
    @GetMapping("/user/{username}")
    public ResponseEntity<List<OrderResponse>> getOrdersByAuthenticatedUser(@PathVariable String username) {
        return ResponseEntity.ok(orderService.getOrdersByUsername(username));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderCreateRequest request,
                                                     Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(orderService.createOrder(request, authentication.getName()));
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderResponse> updateStatus(@PathVariable Long orderId,
                                                      @Valid @RequestBody OrderStatusUpdateRequest request) {
        return ResponseEntity.ok(orderService.changeOrderStatus(orderId, request));
    }

}

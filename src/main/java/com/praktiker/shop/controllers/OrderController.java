package com.praktiker.shop.controllers;

import com.praktiker.shop.entities.order.Order;
import com.praktiker.shop.entities.order.OrderStatus;
import com.praktiker.shop.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<Order>> getOrdersByUsername(@PathVariable String username) {
        return ResponseEntity.ok(orderService.getOrdersByUsername(username));
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.creatOrder(order));
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable Long orderId, @RequestParam OrderStatus status) {
        orderService.changeOrderStatus(orderId, status);
        return ResponseEntity.ok().build();
    }
}

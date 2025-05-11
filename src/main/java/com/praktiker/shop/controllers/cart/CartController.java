package com.praktiker.shop.controllers.cart;

import com.praktiker.shop.dto.cart.CartItemRequest;
import com.praktiker.shop.dto.cart.CartRequest;
import com.praktiker.shop.dto.cart.CartResponse;
import com.praktiker.shop.dto.order.OrderResponse;
import com.praktiker.shop.entities.user.User;
import com.praktiker.shop.services.cart.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<CartResponse> getCart(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(cartService.getCart(user));
    }

    @PostMapping("/item")
    public ResponseEntity<CartResponse> addCartItem(@Valid @RequestBody CartItemRequest request,
                                                    @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.addCartItem(request, user));
    }

    @PatchMapping("item/{id}")
    public ResponseEntity<CartResponse> changeCartItemQuantity(@PathVariable Long id,
                                                               @Valid @RequestBody CartItemRequest request,
                                                               @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(cartService.changeCartItemQuantity(id, request, user));
    }

    @DeleteMapping("item/{id}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long id,
                                               @AuthenticationPrincipal User user) {
        cartService.deleteCartItem(id, user);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<CartResponse> replaceCartItems(@Valid @RequestBody CartRequest request,
                                                         @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(cartService.replaceCartItems(request, user));
    }

    @PostMapping("/checkout")
    public ResponseEntity<OrderResponse> checkout(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.checkout(user));
    }
}

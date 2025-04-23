package com.praktiker.shop.controllers;

import com.praktiker.shop.dto.UserRegisterRequest;
import com.praktiker.shop.entities.user.User;
import com.praktiker.shop.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserRegisterRequest> register(@RequestBody User user) {
        User registered = authService.register(user);
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest
                (registered.getId(),
                registered.getUsername(),
                registered.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(userRegisterRequest);
    }
}

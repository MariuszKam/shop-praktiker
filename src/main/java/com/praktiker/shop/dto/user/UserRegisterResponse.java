package com.praktiker.shop.dto.user;

import com.praktiker.shop.entities.user.Role;

import java.util.Set;

public record UserRegisterResponse(Long id, String username, String email, Set<Role> roles) {
}

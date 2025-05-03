package com.praktiker.shop.dto.user;

import java.util.Set;

public record UserRegisterResponse(Long id, String username, String email, Set<String> roles) {
}

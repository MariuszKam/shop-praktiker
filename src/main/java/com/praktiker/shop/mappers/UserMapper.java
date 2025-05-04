package com.praktiker.shop.mappers;

import com.praktiker.shop.dto.user.UserRegisterRequest;
import com.praktiker.shop.dto.user.UserRegisterResponse;
import com.praktiker.shop.entities.user.Role;
import com.praktiker.shop.entities.user.User;

import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {

    public static User toEntity(UserRegisterRequest request, String password, Set<Role> roles) {
        return User.builder()
                   .username(request.getUsername())
                   .password(password)
                   .email(request.getEmail())
                   .roles(roles)
                   .build();
    }

    public static UserRegisterResponse toResponse(User user) {
        return new UserRegisterResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()));
    }
}

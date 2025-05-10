package com.praktiker.shop.mappers;

import com.praktiker.shop.dto.user.UserRegisterRequest;
import com.praktiker.shop.dto.user.UserRegisterResponse;
import com.praktiker.shop.entities.user.Role;
import com.praktiker.shop.entities.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", source = "encodedPassword")
    @Mapping(target = "roles", source = "roles")
    User toEntity(UserRegisterRequest request, String encodedPassword, Set<Role> roles);

    @Mapping(target = "roles", source = "roles", qualifiedByName = "rolesToNames")
    UserRegisterResponse toResponse(User user);

    @Named("rolesToNames")
    static Set<String> mapRolesToNames(Set<Role> roles) {
        return roles.stream()
                    .map(Role::getName)
                    .collect(Collectors.toSet());
    }
}

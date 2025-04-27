package com.praktiker.shop.utilis.factories;

import com.praktiker.shop.dto.user.UserRegisterRequest;
import com.praktiker.shop.dto.user.UserRegisterResponse;
import com.praktiker.shop.entities.user.Role;

import java.util.Set;

public class UserRegisterTestFactory {

    public static UserRegisterRequest createUserRequest(String username, String password, String email) {
        return new UserRegisterRequest(username, password, email);
    }

    public static UserRegisterRequest createUserRequest() {
        return new UserRegisterRequest("Adam", "Password123", "adam@mail.com");
    }

    public static UserRegisterResponse createUserResponse(Long id, String username,String email, Set<Role> roles) {
        return new UserRegisterResponse(id, username, email, roles);
    }

    public static UserRegisterResponse createUserResponse (){
        return new UserRegisterResponse(
                1L,
                "Adam",
                "adam@mail.com",
                Set.of(new Role(1L, "ROLE_USER")));
    }

}

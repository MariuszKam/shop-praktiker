package com.praktiker.shop.services;

import com.praktiker.shop.dto.user.UserRegisterRequest;
import com.praktiker.shop.dto.user.UserRegisterResponse;
import com.praktiker.shop.entities.user.Role;
import com.praktiker.shop.entities.user.User;
import com.praktiker.shop.exceptions.RoleNotFoundException;
import com.praktiker.shop.mappers.UserMapper;
import com.praktiker.shop.persistance.RoleRepository;
import com.praktiker.shop.persistance.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public UserRegisterResponse register(UserRegisterRequest request) {
        Role role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RoleNotFoundException("Default role ROLE_USER not found!"));

        User user = UserMapper.toEntity(
                request,
                passwordEncoder.encode(request.getPassword()),
                Set.of(role));

        User registered = userRepository.save(user);

        return UserMapper.toResponse(registered);
    }

}

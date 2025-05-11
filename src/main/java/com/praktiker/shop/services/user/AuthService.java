package com.praktiker.shop.services.user;

import com.praktiker.shop.dto.user.UserRegisterRequest;
import com.praktiker.shop.dto.user.UserRegisterResponse;
import com.praktiker.shop.entities.user.Role;
import com.praktiker.shop.entities.user.User;
import com.praktiker.shop.exceptions.user.RoleNotFoundException;
import com.praktiker.shop.mappers.user.UserMapper;
import com.praktiker.shop.persistance.user.RoleRepository;
import com.praktiker.shop.persistance.user.UserRepository;
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

    private final UserMapper userMapper;

    public UserRegisterResponse register(UserRegisterRequest request) {
        Role role = roleRepository.findByName("ROLE_USER")
                                  .orElseThrow(() -> new RoleNotFoundException("Default role ROLE_USER not found!"));

        User user = userMapper.toEntity(
                request,
                passwordEncoder.encode(request.getPassword()),
                Set.of(role));

        User registered = userRepository.save(user);

        return userMapper.toResponse(registered);
    }

}

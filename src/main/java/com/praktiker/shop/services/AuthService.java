package com.praktiker.shop.services;

import com.praktiker.shop.entities.user.Role;
import com.praktiker.shop.entities.user.User;
import com.praktiker.shop.exceptions.RoleNotFoundException;
import com.praktiker.shop.persistance.RoleRepository;
import com.praktiker.shop.persistance.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role defaultRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RoleNotFoundException("Default role ROLE_USER not found!"));
        user.addRole(defaultRole);

        return userRepository.save(user);
    }

}

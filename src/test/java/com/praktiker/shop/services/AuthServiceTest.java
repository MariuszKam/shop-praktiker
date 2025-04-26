package com.praktiker.shop.services;

import com.praktiker.shop.entities.user.Role;
import com.praktiker.shop.entities.user.User;
import com.praktiker.shop.persistance.RoleRepository;
import com.praktiker.shop.persistance.UserRepository;
import com.praktiker.shop.utilis.UserTestFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @Test
    public void shouldRegisterUserWithEncodedPasswordAndDefaultRole() {
        User user = UserTestFactory.createUser();

        Role role = new Role();
        role.setName("ROLE_USER");

        when(passwordEncoder.encode(user.getPassword())).thenReturn("hashed");
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(role));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User registered = authService.register(user);

        assertEquals("hashed", registered.getPassword(), "Password is not hashed");
        assertTrue(registered.getRoles().contains(role));
        verify(userRepository).save(registered);
    }
}

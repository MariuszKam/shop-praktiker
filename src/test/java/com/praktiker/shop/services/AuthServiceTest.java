package com.praktiker.shop.services;

import com.praktiker.shop.dto.user.UserRegisterRequest;
import com.praktiker.shop.dto.user.UserRegisterResponse;
import com.praktiker.shop.entities.user.Role;
import com.praktiker.shop.entities.user.User;
import com.praktiker.shop.persistance.RoleRepository;
import com.praktiker.shop.persistance.UserRepository;
import com.praktiker.shop.utilis.factories.UserRegisterTestFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
        UserRegisterRequest request = UserRegisterTestFactory.createUserRequest();

        Role role = new Role();
        role.setName("ROLE_USER");

        when(passwordEncoder.encode(request.getPassword())).thenReturn("hashed");
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(role));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserRegisterResponse response = authService.register(request);

        assertEquals("Adam", response.username(), "Username is different!");
        assertEquals("adam@mail.com", response.email(), "Email is different!");
        assertTrue(response.roles().contains(role), "Role is different!");

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();

        assertEquals("Adam", savedUser.getUsername(), "Username is different!");
        assertEquals("hashed", savedUser.getPassword(), "Password is not encoded!");
        assertEquals("adam@mail.com", savedUser.getEmail(), "Email is different!");
        assertTrue(savedUser.getRoles().contains(role), "Role is different!");

    }
}

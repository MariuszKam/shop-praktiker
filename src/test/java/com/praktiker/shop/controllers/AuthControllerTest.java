package com.praktiker.shop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.praktiker.shop.config.security.SecurityConfig;
import com.praktiker.shop.dto.user.UserRegisterRequest;
import com.praktiker.shop.dto.user.UserRegisterResponse;
import com.praktiker.shop.services.AuthService;
import com.praktiker.shop.utilis.factories.UserRegisterTestFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import(SecurityConfig.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthService authService;

    @Test
    public void shouldRegisterUser() throws Exception {
        UserRegisterRequest request = UserRegisterTestFactory.createUserRequest();

        UserRegisterResponse response = UserRegisterTestFactory.createUserResponse();

        when(authService.register(any(UserRegisterRequest.class))).thenReturn(response);

        mockMvc.perform(post("/auth/register")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id").value(1L))
               .andExpect(jsonPath("$.username").value("Adam"))
               .andExpect(jsonPath("$.email").value("adam@mail.com"));
    }
}

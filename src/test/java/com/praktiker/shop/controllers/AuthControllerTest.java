package com.praktiker.shop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.praktiker.shop.config.security.SecurityConfig;
import com.praktiker.shop.dto.user.UserRegisterRequest;
import com.praktiker.shop.dto.user.UserRegisterResponse;
import com.praktiker.shop.services.AuthService;
import com.praktiker.shop.utilis.factories.UserTestFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        UserRegisterRequest request = UserTestFactory.createUserRequest();

        UserRegisterResponse response = UserTestFactory.createUserResponse();

        when(authService.register(any(UserRegisterRequest.class))).thenReturn(response);

        MvcResult mvcResult = mockMvc.perform(post("/auth/register")
                                                      .with(csrf())
                                                      .contentType(MediaType.APPLICATION_JSON)
                                                      .content(objectMapper.writeValueAsString(request)))
                                     .andExpect(status().isCreated())
                                     .andReturn();

        String json = mvcResult.getResponse().getContentAsString();

        UserRegisterResponse actual = objectMapper.readValue(json, UserRegisterResponse.class);

        assertEquals(response, actual);
    }
}

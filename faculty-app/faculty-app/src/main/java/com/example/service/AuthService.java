package com.example.service;

import com.example.model.dto.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

public class AuthService {

    public LoginResponse login(String email, String password, HttpServletRequest request) throws Exception {
        Map<String, String> body = new HashMap<>();
        body.put("email", email);
        body.put("password", password);

        return ApiClient.post(
                "/auth/login",
                body,
                LoginResponse.class,
                request
        );
    }
}

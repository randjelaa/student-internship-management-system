package com.example.service;

import com.example.dto.ChangePasswordRequest;
import com.example.util.ApiClient;
import jakarta.servlet.http.HttpServletRequest;

public class PasswordService {

    public void changePassword(String current, String newPass, HttpServletRequest request) throws Exception {
        ChangePasswordRequest body = new ChangePasswordRequest();
        body.setCurrentPassword(current);
        body.setNewPassword(newPass);

        ApiClient.post("/auth/change-password", body, Object.class, request);
    }
}
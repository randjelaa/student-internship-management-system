package com.example.util;

import com.example.dto.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AuthUtil {

    public static LoginResponse requireUser(HttpServletRequest request,
                                            HttpServletResponse response) throws IOException {

        LoginResponse user =
                (LoginResponse) request.getSession().getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return null;
        }

        return user;
    }
}
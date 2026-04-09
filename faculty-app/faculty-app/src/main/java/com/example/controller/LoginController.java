package com.example.controller;

import com.example.dto.LoginResponse;
import com.example.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    private final AuthService authService = new AuthService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (email == null || !email.contains("@")
                || password == null || password.length() < 6) {

            req.setAttribute("error", "Invalid input data");
            doGet(req, resp);
            return;
        }

        try {
            LoginResponse response = authService.login(email, password, req);
            req.getSession().setAttribute("user", response);
            resp.sendRedirect(req.getContextPath() + "/companies");
        } catch (Exception e) {
            req.setAttribute("error", "Incorrect email or password");
            doGet(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }
}
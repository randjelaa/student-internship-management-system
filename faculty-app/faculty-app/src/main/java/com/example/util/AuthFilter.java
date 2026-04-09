package com.example.util;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String path = req.getRequestURI();

        if (isPublic(path)) {
            chain.doFilter(request, response);
            return;
        }

        if (path.endsWith("/login")) {
            Object user = req.getSession().getAttribute("user");

            if (user != null) {
                resp.sendRedirect(req.getContextPath() + "/companies");
                return;
            }
        }

        Object user = req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        chain.doFilter(request, response);
    }

    private boolean isPublic(String path) {
        return path.endsWith("/login") ||
                path.endsWith("/logout") ||
                path.contains("/css/") ||
                path.contains("/js/") ||
                path.contains("/images/");
    }
}
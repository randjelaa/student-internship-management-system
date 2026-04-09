package com.example.controller;

import com.example.dto.CompanyResponseDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.example.dto.CompanySummaryDTO;
import com.example.service.CompanyService;

@WebServlet("/companies")
public class CompaniesController extends HttpServlet {

    private final CompanyService service = new CompanyService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");

        try {
            if (idParam != null) {
                Long id = Long.parseLong(idParam);

                CompanyResponseDTO company = service.getById(id, req);
                req.setAttribute("company", company);

                req.getRequestDispatcher("WEB-INF/pages/company-details.jsp").forward(req, resp);
            } else {
                CompanySummaryDTO[] companies = service.getAll(req);
                req.setAttribute("companies", companies);

                req.getRequestDispatcher("WEB-INF/pages/companies.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        try {
            if ("create".equals(action)) {
                String email = req.getParameter("email");
                String password = req.getParameter("password");
                String name = req.getParameter("name");
                String website = req.getParameter("website");
                String description = req.getParameter("description");

                String error = null;
                if (email == null || !email.contains("@")) {
                    error = "Invalid email";
                } else if (password == null || password.length() < 6) {
                    error = "Password must be at least 6 characters";
                } else if (name == null || name.trim().isEmpty()) {
                    error = "Name is required";
                } else if (website != null && !website.trim().isEmpty() && !website.startsWith("http")) {
                    error = "Website must start with http";
                }

                if (error != null) {
                    req.setAttribute("error", error);
                    doGet(req, resp);
                }

                service.create(email, password, name, description, website, req);

            } else if ("activate".equals(action)) {
                Long id = Long.parseLong(req.getParameter("id"));
                service.activate(id, req);
            } else if ("deactivate".equals(action)) {
                Long id = Long.parseLong(req.getParameter("id"));
                service.deactivate(id, req);
            } else {
                req.setAttribute("error", "Unknown action");
                doGet(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Server error: " + e.getMessage());
            doGet(req, resp);
        }

        resp.sendRedirect(req.getContextPath() + "/companies");
    }
}

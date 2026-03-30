package com.example.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.example.model.dto.CompanySummaryDTO;
import com.example.service.CompanyService;

@WebServlet("/companies")
public class CompaniesController extends HttpServlet {

    private final CompanyService service = new CompanyService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            CompanySummaryDTO[] companies = service.getAll(req);
            req.setAttribute("companies", companies);

            req.getRequestDispatcher("WEB-INF/pages/companies.jsp")
                    .forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getParameter("action");

        try {
            if ("create".equals(action)) {
                String email = req.getParameter("email");
                String password = req.getParameter("password");
                String name = req.getParameter("name");
                String description = req.getParameter("description");
                String website = req.getParameter("website");

                service.create(email, password, name, description, website, req);
            }

            if ("activate".equals(action)) {
                Long id = Long.parseLong(req.getParameter("id"));
                service.activate(id, req);
            }

            if ("deactivate".equals(action)) {
                Long id = Long.parseLong(req.getParameter("id"));
                service.deactivate(id, req);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        resp.sendRedirect("companies");
    }
}

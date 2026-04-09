package com.example.controller;

import com.example.service.InternshipService;
import com.example.util.PageResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/internships")
public class InternshipsController extends HttpServlet {

    private final InternshipService service = new InternshipService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = 0;

        try {
            if (req.getParameter("page") != null) {
                page = Integer.parseInt(req.getParameter("page"));
            }

            PageResponse<?> response = service.getAll(page, req);

            req.setAttribute("page", response);
            req.setAttribute("currentPage", page);

            req.getRequestDispatcher("WEB-INF/pages/internships.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}

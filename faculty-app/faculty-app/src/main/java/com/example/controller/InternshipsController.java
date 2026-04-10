package com.example.controller;

import com.example.dto.CompanyResponseDTO;
import com.example.dto.CompanySummaryDTO;
import com.example.dto.InternshipResponseDTO;
import com.example.dto.InternshipSummaryDTO;
import com.example.service.InternshipService;
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        String idParam = req.getParameter("id");

        try {
            if (idParam != null) {
                Long id = Long.parseLong(idParam);

                InternshipResponseDTO internship = service.getById(id, req);
                req.setAttribute("internship", internship);

                req.getRequestDispatcher("WEB-INF/pages/internship-details.jsp").forward(req, resp);
            } else {
                InternshipSummaryDTO[] internships = service.getAll(req);
                req.setAttribute("internships", internships);

                req.getRequestDispatcher("WEB-INF/pages/internships.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}

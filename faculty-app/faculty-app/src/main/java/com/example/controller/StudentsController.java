package com.example.controller;

import com.example.model.dto.StudentResponseDTO;
import com.example.service.StudentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;

@WebServlet("/students")
@MultipartConfig
public class StudentsController extends HttpServlet {

    private final StudentService service = new StudentService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            StudentResponseDTO[] students = service.getAll(req);
            req.setAttribute("students", students);

            req.getRequestDispatcher("WEB-INF/pages/students.jsp")
                    .forward(req, resp);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String action = req.getParameter("action");

        try {
            if ("create".equals(action)) {
                service.create(req);
            }

            if ("upload".equals(action)) {
                Part filePart = req.getPart("file");
                service.uploadCsv(filePart, req);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        resp.sendRedirect("students");
    }
}

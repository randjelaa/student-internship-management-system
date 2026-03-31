package com.example.controller;

import com.example.dto.StudentResponseDTO;
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
            String action = req.getParameter("action");

            if ("edit".equals(action)) {
                Long id = Long.parseLong(req.getParameter("id"));

                try {
                    StudentResponseDTO student = service.getById(id, req);

                    req.setAttribute("student", student);

                    req.getRequestDispatcher("WEB-INF/pages/update-student.jsp")
                            .forward(req, resp);

                    return;

                } catch (Exception e) {
                    throw new ServletException(e);
                }
            }

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

            if ("delete".equals(action)) {
                Long id = Long.parseLong(req.getParameter("id"));
                service.delete(id, req);
            }

            if ("update".equals(action)) {
                Long id = Long.parseLong(req.getParameter("id"));
                service.update(id, req);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        resp.sendRedirect("students");
    }
}

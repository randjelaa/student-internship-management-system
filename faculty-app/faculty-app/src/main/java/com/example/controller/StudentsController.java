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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try {
            String action = req.getParameter("action");

            if ("edit".equals(action)) {
                Long id = Long.parseLong(req.getParameter("id"));

                try {
                    StudentResponseDTO student = service.getById(id, req);
                    req.setAttribute("student", student);
                    req.getRequestDispatcher("WEB-INF/pages/update-student.jsp").forward(req, resp);
                    return;
                } catch (Exception e) {
                    throw new ServletException(e);
                }
            }

            StudentResponseDTO[] students = service.getAll(req);
            req.setAttribute("students", students);

            req.getRequestDispatcher("WEB-INF/pages/students.jsp").forward(req, resp);
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
                String firstName = req.getParameter("firstName");
                String lastName = req.getParameter("lastName");
                String index = req.getParameter("indexNumber");
                String faculty = req.getParameter("faculty");
                String yearStr = req.getParameter("yearOfStudy");

                int year = 0;
                try { year = Integer.parseInt(yearStr); } catch (Exception ignored) {}

                if (email == null || !email.contains("@")
                        || password == null || password.length() < 6
                        || firstName == null || firstName.isEmpty()
                        || lastName == null || lastName.isEmpty()
                        || index == null || index.isEmpty()
                        || faculty == null || faculty.isEmpty()
                        || year < 1 || year > 6) {

                    req.setAttribute("error", "Invalid input data");
                    doGet(req, resp);
                    return;
                }

                service.create(req);
            }

            if ("update".equals(action)) {
                String email = req.getParameter("email");
                String firstName = req.getParameter("firstName");
                String lastName = req.getParameter("lastName");
                String yearStr = req.getParameter("yearOfStudy");

                int year = 0;
                try { year = Integer.parseInt(yearStr); } catch (Exception ignored) {}

                if (email == null || !email.contains("@")
                        || firstName == null || firstName.isEmpty()
                        || lastName == null || lastName.isEmpty()
                        || year < 1 || year > 6) {

                    req.setAttribute("error", "Invalid input data");
                    doGet(req, resp);
                    return;
                }

                Long id = Long.parseLong(req.getParameter("id"));
                service.update(id, req);
            }

            if ("delete".equals(action)) {
                Long id = Long.parseLong(req.getParameter("id"));
                service.delete(id, req);
            }

            if ("upload".equals(action)) {
                Part filePart = req.getPart("file");

                if (filePart == null || filePart.getSize() == 0) {
                    req.setAttribute("error", "File is required");
                    doGet(req, resp);
                    return;
                }

                if (!filePart.getSubmittedFileName().endsWith(".csv")) {
                    req.setAttribute("error", "Only CSV files allowed");
                    doGet(req, resp);
                    return;
                }

                service.uploadCsv(filePart, req);
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Unexpected error occurred");
            doGet(req, resp);
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/students");
    }
}

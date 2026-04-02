package com.example.controller;

import com.example.dto.GradeDetails;
import com.example.service.GradeService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/grades")
public class GradesController extends HttpServlet {

    private final GradeService service = new GradeService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            GradeDetails[] grades = service.getAll(req);
            req.setAttribute("grades", grades);

            req.getRequestDispatcher("WEB-INF/pages/grades.jsp")
                    .forward(req, resp);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        try {
            Long gradeId = Long.parseLong(req.getParameter("gradeId"));
            int facultyGrade = Integer.parseInt(req.getParameter("facultyGrade"));

            service.grade(gradeId, facultyGrade, req);

        } catch (Exception e) {
            e.printStackTrace();
        }

        resp.sendRedirect("grades");
    }
}

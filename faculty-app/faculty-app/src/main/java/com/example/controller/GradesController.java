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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            GradeDetails[] grades = service.getAll(req);
            req.setAttribute("grades", grades);

            req.getRequestDispatcher("WEB-INF/pages/grades.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long gradeId = Long.parseLong(req.getParameter("gradeId"));
            String gradeParam = req.getParameter("facultyGrade");

            if (gradeParam == null || gradeParam.isEmpty()) {
                req.setAttribute("error", "Grade is required");
                doGet(req, resp);
                return;
            }

            int facultyGrade = Integer.parseInt(gradeParam);

            if (facultyGrade < 6 || facultyGrade > 10) {
                req.setAttribute("error", "Grade must be between 6 and 10");
                doGet(req, resp);
                return;
            }

            service.grade(gradeId, facultyGrade, req);
        } catch (NumberFormatException e) {
            req.setAttribute("error", "Invalid grade format");
            doGet(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Unexpected error occurred");
            doGet(req, resp);
        }

        resp.sendRedirect(req.getContextPath() + "/grades");
    }
}

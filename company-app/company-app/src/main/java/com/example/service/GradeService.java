package com.example.service;

import com.example.dto.CreateGradeRequest;
import com.example.dto.InternshipGradingGroupDTO;
import com.example.util.ApiClient;
import jakarta.servlet.http.HttpServletRequest;

public class GradeService {

    public InternshipGradingGroupDTO[] getDashboard(HttpServletRequest request) throws Exception {
        return ApiClient.get("/grades/dashboard", InternshipGradingGroupDTO[].class, request);
    }

    public void createGrade(Long studentId, Long internshipId, String comment, HttpServletRequest request) throws Exception {
        CreateGradeRequest body = new CreateGradeRequest();
        body.setStudentId(studentId);
        body.setInternshipId(internshipId);
        body.setCompanyComment(comment);
        body.setFacultyGrade(null);

        ApiClient.post("/grades", body, Object.class, request);
    }
}

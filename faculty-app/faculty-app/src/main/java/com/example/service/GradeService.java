package com.example.service;

import com.example.dto.GradeDetails;
import com.example.util.ApiClient;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

public class GradeService {

    public GradeDetails[] getAll(HttpServletRequest request) throws Exception {
        return ApiClient.get("/grades/details", GradeDetails[].class, request);
    }

    public void grade(Long gradeId, int facultyGrade, HttpServletRequest request) throws Exception {

        Map<String, Object> body = new HashMap<>();
        body.put("facultyGrade", facultyGrade);

        ApiClient.put("/grades/" + gradeId, body, Object.class, request);
    }
}

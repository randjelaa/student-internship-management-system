package com.example.service;

import com.example.dto.InternshipGradingGroupDTO;
import com.example.util.ApiClient;
import jakarta.servlet.http.HttpServletRequest;

public class GradeService {

    public InternshipGradingGroupDTO[] getDashboard(HttpServletRequest request) throws Exception {
        return ApiClient.get("/grades/dashboard", InternshipGradingGroupDTO[].class, request);
    }
}

package com.example.service;

import com.example.util.ApiClient;
import jakarta.servlet.http.HttpServletRequest;
import com.example.model.dto.InternshipSummaryDTO;
import com.example.util.PageResponse;

public class InternshipService {

    public PageResponse<InternshipSummaryDTO> getAll(int page, HttpServletRequest request) throws Exception {
        String path = "/internships?page=" + page + "&size=10";
        return ApiClient.get(path, PageResponse.class, request);
    }
}
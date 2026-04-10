package com.example.service;

import com.example.dto.CompanyResponseDTO;
import com.example.dto.InternshipResponseDTO;
import com.example.util.ApiClient;
import jakarta.servlet.http.HttpServletRequest;
import com.example.dto.InternshipSummaryDTO;

public class InternshipService {

    public InternshipSummaryDTO[] getAll(HttpServletRequest request) throws Exception {
        String path = "/internships";
        return ApiClient.get(path, InternshipSummaryDTO[].class, request);
    }

    public InternshipResponseDTO getById(Long id, HttpServletRequest request) throws Exception {
        return ApiClient.get("/internships/" + id, InternshipResponseDTO.class, request);
    }
}
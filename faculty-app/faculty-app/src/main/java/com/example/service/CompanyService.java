package com.example.service;

import com.example.model.dto.CompanyResponseDTO;
import com.example.util.ApiClient;
import jakarta.servlet.http.HttpServletRequest;
import com.example.model.dto.CompanySummaryDTO;

import java.util.HashMap;
import java.util.Map;

public class CompanyService {

    public CompanySummaryDTO[] getAll(HttpServletRequest request) throws Exception {
        return ApiClient.get("/companies", CompanySummaryDTO[].class, request);
    }

    public CompanyResponseDTO getById(Long id, HttpServletRequest request) throws Exception {
        return ApiClient.get("/companies/" + id, CompanyResponseDTO.class, request);
    }

    public void activate(Long id, HttpServletRequest request) throws Exception {
        ApiClient.post("/companies/" + id + "/activate", null, Object.class, request);
    }

    public void deactivate(Long id, HttpServletRequest request) throws Exception {
        ApiClient.post("/companies/" + id + "/deactivate", null, Object.class, request);
    }

    public void create(String email, String password, String name,
                       String description, String website,
                       HttpServletRequest request) throws Exception {

        Map<String, String> body = new HashMap<>();

        body.put("email", email);
        body.put("password", password);
        body.put("name", name);
        body.put("description", description);
        body.put("website", website);

        ApiClient.post("/companies", body, Object.class, request);
    }
}

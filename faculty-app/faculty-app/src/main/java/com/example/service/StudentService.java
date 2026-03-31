package com.example.service;

import com.example.dto.StudentResponseDTO;
import com.example.util.ApiClient;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

import java.util.HashMap;
import java.util.Map;

public class StudentService {

    public StudentResponseDTO[] getAll(HttpServletRequest request) throws Exception {
        return ApiClient.get("/students", StudentResponseDTO[].class, request);
    }

    public void create(HttpServletRequest request) throws Exception {

        Map<String, Object> body = new HashMap<>();

        body.put("email", request.getParameter("email"));
        body.put("password", request.getParameter("password"));
        body.put("firstName", request.getParameter("firstName"));
        body.put("lastName", request.getParameter("lastName"));
        body.put("indexNumber", request.getParameter("indexNumber"));
        body.put("faculty", request.getParameter("faculty"));
        body.put("yearOfStudy", Integer.parseInt(request.getParameter("yearOfStudy")));

        ApiClient.post("/students", body, Object.class, request);
    }

    public void uploadCsv(Part filePart, HttpServletRequest request) throws Exception {
        ApiClient.postMultipart("/students/import-csv", filePart, request);
    }

    public void delete(Long id, HttpServletRequest request) throws Exception {
        ApiClient.delete("/students/" + id, request);
    }

    public StudentResponseDTO getById(Long id, HttpServletRequest request) throws Exception {
        return ApiClient.get("/students/" + id, StudentResponseDTO.class, request);
    }

    public void update(Long id, HttpServletRequest req) throws Exception {

        Map<String, Object> body = new HashMap<>();

        body.put("email", req.getParameter("email"));
        if (req.getParameter("password") != null && !req.getParameter("password").isEmpty()) {
            body.put("password", req.getParameter("password"));
        }
        body.put("firstName", req.getParameter("firstName"));
        body.put("lastName", req.getParameter("lastName"));
        body.put("indexNumber", req.getParameter("indexNumber"));
        body.put("faculty", req.getParameter("faculty"));
        body.put("yearOfStudy", Integer.parseInt(req.getParameter("yearOfStudy")));

        ApiClient.put("/students/" + id, body, Object.class, req);
    }
}
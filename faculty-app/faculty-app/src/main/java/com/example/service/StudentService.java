package com.example.service;

import com.example.model.dto.StudentResponseDTO;
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
}
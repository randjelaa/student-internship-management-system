package com.example.service;

import com.example.dto.CreateInternshipRequest;
import com.example.dto.InternshipResponseDTO;
import com.example.dto.UpdateInternshipRequest;
import com.example.util.ApiClient;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.List;

public class InternshipService {

    public List<InternshipResponseDTO> getMyInternships(HttpServletRequest request) throws Exception {
        InternshipResponseDTO[] response =
                ApiClient.get("/internships/my", InternshipResponseDTO[].class, request);

        return Arrays.asList(response);
    }

    public void create(CreateInternshipRequest req, HttpServletRequest request) throws Exception {
        ApiClient.post("/internships", req, Object.class, request);
    }

    public InternshipResponseDTO getById(Long id, HttpServletRequest request) throws Exception {
        return ApiClient.get("/internships/" + id, InternshipResponseDTO.class, request);
    }

    public void update(Long internshipId, UpdateInternshipRequest dto, HttpServletRequest request) throws Exception {
        if (internshipId == null) {
            throw new IllegalArgumentException("ID is required for update");
        }

        ApiClient.put(
                "/internships/" + internshipId,
                dto,
                Void.class,
                request
        );
    }

    public void delete(Long id, HttpServletRequest request) throws Exception {
        if (id == null) {
            throw new IllegalArgumentException("ID is required for delete");
        }

        ApiClient.delete(
                "/internships/" + id,
                request
        );
    }
}
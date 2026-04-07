package com.example.service;

import com.example.dto.InternshipApplicationsGroupDTO;
import com.example.util.ApiClient;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.List;

public class ApplicationService {
    public List<InternshipApplicationsGroupDTO> getGrouped(HttpServletRequest request) throws Exception {
        InternshipApplicationsGroupDTO[] response =
                ApiClient.get("/applications/company-grouped",
                        InternshipApplicationsGroupDTO[].class,
                        request);

        return Arrays.asList(response);
    }

    public void accept(Long id, HttpServletRequest request) throws Exception {
        ApiClient.post("/applications/" + id + "/accept", null, Object.class, request);
    }

    public void reject(Long id, HttpServletRequest request) throws Exception {
        ApiClient.post("/applications/" + id + "/reject", null, Object.class, request);
    }
}
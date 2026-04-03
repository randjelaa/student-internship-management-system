package com.example.service;

import com.example.dto.TechnologyResponseDTO;
import com.example.util.ApiClient;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TechnologyService {

    public List<TechnologyResponseDTO> getAll(HttpServletRequest request) throws Exception {
        TechnologyResponseDTO[] response =
                ApiClient.get("/technologies", TechnologyResponseDTO[].class, request);

        return Arrays.asList(response);
    }

    public void create(String name, HttpServletRequest request) throws Exception {
        Map<String, String> body = new HashMap<>();
        body.put("name", name);

        ApiClient.post("/technologies", body, Object.class, request);
    }
}
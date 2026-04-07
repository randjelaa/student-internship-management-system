package com.example.service;

import com.example.dto.CvResponseDTO;
import com.example.util.ApiClient;
import jakarta.servlet.http.HttpServletRequest;

public class CvService {

    public CvResponseDTO getByStudentId(Long studentId, HttpServletRequest request) throws Exception {
        return ApiClient.get("/cv/student/" + studentId, CvResponseDTO.class, request);
    }

    public byte[] getPdf(Long studentId, HttpServletRequest request) throws Exception {
        return ApiClient.getBytes("/cv/pdf/" + studentId, request);
    }

    public byte[] getPhoto(Long studentId, HttpServletRequest request) throws Exception {
        return ApiClient.getBytes("/cv/photo/" + studentId, request);
    }
}
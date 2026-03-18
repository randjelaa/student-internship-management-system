package com.example.internships.controller;

import com.example.internships.dto.cv.CreateCvRequest;
import com.example.internships.dto.cv.CvResponseDTO;
import com.example.internships.service.CvService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students/{studentId}/cv")
@RequiredArgsConstructor
public class CvController {

    private final CvService cvService;

    @GetMapping
    public CvResponseDTO getCv(@PathVariable Long studentId) {
        return cvService.getCvByStudentId(studentId);
    }

    @PostMapping
    public CvResponseDTO createCv(@PathVariable Long studentId,
                       @RequestBody CreateCvRequest request) {
        return cvService.createCv(studentId, request);
    }

    @PutMapping
    public CvResponseDTO updateCv(@PathVariable Long studentId,
                       @RequestBody CreateCvRequest request) {
        return cvService.updateCv(studentId, request);
    }

    @DeleteMapping
    public void deleteCv(@PathVariable Long studentId) {
        cvService.deleteCv(studentId);
    }
}

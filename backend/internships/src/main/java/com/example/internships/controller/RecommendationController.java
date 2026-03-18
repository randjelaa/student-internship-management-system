package com.example.internships.controller;

import com.example.internships.dto.recommendation.RecommendationResponseDTO;
import com.example.internships.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @PostMapping("/generate")
    public List<RecommendationResponseDTO> generate(
            @RequestParam Long studentId) {

        return recommendationService.generate(studentId);
    }

    @GetMapping("/{studentId}")
    public List<RecommendationResponseDTO> getByStudent(
            @PathVariable Long studentId) {

        return recommendationService.getByStudent(studentId);
    }
}
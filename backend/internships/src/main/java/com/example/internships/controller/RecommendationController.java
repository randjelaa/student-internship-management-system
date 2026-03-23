package com.example.internships.controller;

import com.example.internships.dto.recommendation.RecommendationResponseDTO;
import com.example.internships.security.UserPrincipal;
import com.example.internships.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @PostMapping("/generate")
    public List<RecommendationResponseDTO> generate(
            Authentication authentication           ) {
        Long userId = ((UserPrincipal) Objects.requireNonNull(authentication.getPrincipal())).getUser().getId();
        return recommendationService.generate(userId);
    }

    @GetMapping()
    public List<RecommendationResponseDTO> getByStudent(
            Authentication authentication) {
        Long userId = ((UserPrincipal) Objects.requireNonNull(authentication.getPrincipal())).getUser().getId();
        return recommendationService.getByStudent(userId);
    }
}
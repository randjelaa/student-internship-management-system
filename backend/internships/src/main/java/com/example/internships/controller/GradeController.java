package com.example.internships.controller;

import com.example.internships.dto.grade.*;
import com.example.internships.dto.specific.InternshipGradingGroupDTO;
import com.example.internships.security.UserPrincipal;
import com.example.internships.service.GradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/grades")
@RequiredArgsConstructor
public class GradeController {

    private final GradeService gradeService;

    @PostMapping
    public ResponseEntity<GradeResponseDTO> createGrade(@RequestBody CreateGradeRequest request) {
        GradeResponseDTO created = gradeService.createGrade(request);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<List<GradeResponseDTO>> getGradesByStudentId(@PathVariable Long studentId) {
        List<GradeResponseDTO> grades = gradeService.getGradesByStudentId(studentId);
        return ResponseEntity.ok(grades);
    }

    @GetMapping("/details")
    public List<GradeDetails> getGradeDetails() {
        return gradeService.getAllGradeDetails();
    }

    @PutMapping("/{id}")
    public ResponseEntity<GradeResponseDTO> updateGrade(
            @PathVariable Long id,
            @RequestBody CreateGradeRequest request) {

        GradeResponseDTO updated = gradeService.updateGrade(id, request);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/dashboard")
    public List<InternshipGradingGroupDTO> getGradingDashboard(Authentication authentication) {
        Long userId = ((UserPrincipal) Objects.requireNonNull(authentication.getPrincipal())).getUser().getId();
        return gradeService.getGradingDashboard(userId);
    }
}
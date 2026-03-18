package com.example.internships.controller;

import com.example.internships.dto.grade.*;
import com.example.internships.service.GradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
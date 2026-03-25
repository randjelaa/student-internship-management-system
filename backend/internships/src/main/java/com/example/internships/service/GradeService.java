package com.example.internships.service;

import com.example.internships.dto.grade.*;
import com.example.internships.entity.Grade;
import com.example.internships.entity.Student;
import com.example.internships.entity.Internship;
import com.example.internships.mapper.GradeMapper;
import com.example.internships.repository.GradeRepository;
import com.example.internships.repository.StudentRepository;
import com.example.internships.repository.InternshipRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeService {

    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;
    private final InternshipRepository internshipRepository;
    private final GradeMapper gradeMapper;

    @Transactional
    public GradeResponseDTO createGrade(CreateGradeRequest request) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Internship internship = internshipRepository.findById(request.getInternshipId())
                .orElseThrow(() -> new RuntimeException("Internship not found"));

        Grade grade = gradeMapper.toEntity(request);
        grade.setStudent(student);
        grade.setInternship(internship);

        Grade saved = gradeRepository.save(grade);
        return gradeMapper.toResponse(saved);
    }

    public List<GradeResponseDTO> getGradesByStudentId(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return gradeRepository.findByStudent(student)
                .stream()
                .map(gradeMapper::toResponse)
                .toList();
    }
}
package com.example.internships.service;

import com.example.internships.dto.grade.*;
import com.example.internships.dto.worklog.WorkLogResponseDTO;
import com.example.internships.entity.Grade;
import com.example.internships.entity.Student;
import com.example.internships.entity.Internship;
import com.example.internships.mapper.GradeMapper;
import com.example.internships.mapper.InternshipMapper;
import com.example.internships.mapper.StudentMapper;
import com.example.internships.mapper.WorkLogMapper;
import com.example.internships.repository.GradeRepository;
import com.example.internships.repository.StudentRepository;
import com.example.internships.repository.InternshipRepository;
import com.example.internships.repository.WorkLogRepository;
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
    private final WorkLogRepository workLogRepository;
    private final GradeMapper gradeMapper;
    private final WorkLogMapper workLogMapper;
    private final StudentMapper studentMapper;
    private final InternshipMapper internshipMapper;

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

    public List<GradeDetails> getAllGradeDetails() {
        List<Grade> grades = gradeRepository.findAll();

        return grades.stream().map(grade -> {
            List<WorkLogResponseDTO> workLogs = workLogRepository.findByStudentIdAndInternshipId(grade.getStudent().getId(), grade.getInternship().getId())
                    .stream()
                    .map(workLogMapper::toResponse)
                    .toList();

            return new GradeDetails(
                    grade.getId(),
                    studentMapper.toSummary(grade.getStudent()),
                    internshipMapper.toSummary(grade.getInternship()),
                    grade.getCompanyComment(),
                    grade.getFacultyGrade(),
                    workLogs
            );

        }).toList();
    }

    public GradeResponseDTO updateGrade(Long id, CreateGradeRequest request) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grade not found"));

        grade.setFacultyGrade(request.getFacultyGrade());
        gradeRepository.save(grade);

        return gradeMapper.toResponse(grade);
    }
}
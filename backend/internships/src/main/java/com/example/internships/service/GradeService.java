package com.example.internships.service;

import com.example.internships.entity.Grade;
import com.example.internships.repository.GradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeService {

    private final GradeRepository gradeRepository;

    public List<Grade> getAllGrades() {
        return gradeRepository.findAll();
    }

    public Grade getGradeById(Long id) {
        return gradeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grade not found with id: " + id));
    }

    public Grade createGrade(Grade grade) {
        return gradeRepository.save(grade);
    }

    public Grade updateGrade(Long id, Grade updatedGrade) {
        Grade existing = getGradeById(id);

        existing.setStudent(updatedGrade.getStudent());
        existing.setInternship(updatedGrade.getInternship());
        existing.setCompanyComment(updatedGrade.getCompanyComment());
        existing.setFacultyGrade(updatedGrade.getFacultyGrade());

        return gradeRepository.save(existing);
    }

    public void deleteGrade(Long id) {
        Grade grade = getGradeById(id);

        gradeRepository.delete(grade);
    }
}
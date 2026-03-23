package com.example.internships.service;

import com.example.internships.dto.enums.ApplicationStatus;
import com.example.internships.entity.Application;
import com.example.internships.entity.Internship;
import com.example.internships.entity.Student;
import com.example.internships.repository.ApplicationRepository;
import com.example.internships.repository.InternshipRepository;
import com.example.internships.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final StudentRepository studentRepository;
    private final InternshipRepository internshipRepository;

    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    public Application getApplicationById(Long id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));
    }

    public Application createApplication(Long userId, Long internshipId) {
        Student student = studentRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Internship internship = internshipRepository.findById(internshipId)
                .orElseThrow(() -> new RuntimeException("Internship not found"));

        Application application = new Application();
        application.setStudent(student);
        application.setInternship(internship);
        application.setStatus(ApplicationStatus.PENDING);

        return applicationRepository.save(application);
    }

    public Application acceptApplication(Long id) {
        Application application = getApplicationById(id);
        application.setStatus(ApplicationStatus.ACCEPTED);
        return applicationRepository.save(application);
    }

    public Application rejectApplication(Long id) {
        Application application = getApplicationById(id);
        application.setStatus(ApplicationStatus.REJECTED);
        return applicationRepository.save(application);
    }

    public void deleteApplication(Long id) {
        Application application = getApplicationById(id);
        applicationRepository.delete(application);
    }

    public List<Application> getApplicationsByUserId(Long userId) {
        Long studentId = studentRepository.findByUserId(userId).orElseThrow().getId();
        return applicationRepository.findByStudentId(studentId);
    }
}
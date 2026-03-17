package com.example.internships.service;

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

    public Application createApplication(Long studentId, Long internshipId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Internship internship = internshipRepository.findById(internshipId)
                .orElseThrow(() -> new RuntimeException("Internship not found"));

        Application application = new Application();
        application.setStudent(student);
        application.setInternship(internship);
        application.setStatus("PENDING");

        return applicationRepository.save(application);
    }

    public Application acceptApplication(Long id) {
        Application application = getApplicationById(id);
        application.setStatus("ACCEPTED");
        return applicationRepository.save(application);
    }

    public Application rejectApplication(Long id) {
        Application application = getApplicationById(id);
        application.setStatus("REJECTED");
        return applicationRepository.save(application);
    }

    public void deleteApplication(Long id) {
        Application application = getApplicationById(id);
        applicationRepository.delete(application);
    }
}
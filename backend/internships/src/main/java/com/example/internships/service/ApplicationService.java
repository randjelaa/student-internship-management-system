package com.example.internships.service;

import com.example.internships.entity.Application;
import com.example.internships.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    public Application getApplicationById(Long id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found with id: " + id));
    }

    public Application createApplication(Application application) {
        return applicationRepository.save(application);
    }

    public Application updateApplication(Long id, Application updatedApplication) {
        Application existing = getApplicationById(id);

        existing.setStudent(updatedApplication.getStudent());
        existing.setInternship(updatedApplication.getInternship());
        existing.setStatus(updatedApplication.getStatus());

        return applicationRepository.save(existing);
    }

    public void deleteApplication(Long id) {
        Application application = getApplicationById(id);

        applicationRepository.delete(application);
    }
}
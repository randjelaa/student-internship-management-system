package com.example.internships.controller;

import com.example.internships.dto.application.ApplicationResponseDTO;
import com.example.internships.dto.application.CreateApplicationRequest;
import com.example.internships.mapper.ApplicationMapper;
import com.example.internships.entity.Application;
import com.example.internships.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;
    private final ApplicationMapper applicationMapper;

    @GetMapping
    public List<ApplicationResponseDTO> getAllApplications() {
        return applicationService.getAllApplications()
                .stream()
                .map(applicationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ApplicationResponseDTO getApplication(@PathVariable Long id) {
        return applicationMapper.toResponse(applicationService.getApplicationById(id));
    }

    @PostMapping
    public ApplicationResponseDTO createApplication(@RequestBody CreateApplicationRequest request) {
        Application application = applicationService.createApplication(request.getStudentId(), request.getInternshipId());
        return applicationMapper.toResponse(application);
    }

    @PatchMapping("/{id}/accept")
    public ApplicationResponseDTO acceptApplication(@PathVariable Long id) {
        return applicationMapper.toResponse(applicationService.acceptApplication(id));
    }

    @PatchMapping("/{id}/reject")
    public ApplicationResponseDTO rejectApplication(@PathVariable Long id) {
        return applicationMapper.toResponse(applicationService.rejectApplication(id));
    }

    @DeleteMapping("/{id}")
    public void deleteApplication(@PathVariable Long id) {
        applicationService.deleteApplication(id);
    }
}
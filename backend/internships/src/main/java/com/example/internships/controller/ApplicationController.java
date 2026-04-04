package com.example.internships.controller;

import com.example.internships.dto.application.ApplicationResponseDTO;
import com.example.internships.dto.application.CreateApplicationRequest;
import com.example.internships.dto.internship.InternshipApplicationsDTO;
import com.example.internships.dto.specific.CompanyApplicationViewDTO;
import com.example.internships.dto.specific.InternshipApplicationsGroupDTO;
import com.example.internships.mapper.ApplicationMapper;
import com.example.internships.entity.Application;
import com.example.internships.security.UserPrincipal;
import com.example.internships.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
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

    @GetMapping("/company")
    public List<InternshipApplicationsDTO> getByCompany(Authentication authentication) {
        Long userId = ((UserPrincipal) Objects.requireNonNull(authentication.getPrincipal())).getUser().getId();
        return applicationService.getByCompany(userId);
    }

    @GetMapping("/{id}")
    public ApplicationResponseDTO getApplication(@PathVariable Long id) {
        return applicationMapper.toResponse(applicationService.getApplicationById(id));
    }

    @PostMapping
    public ApplicationResponseDTO createApplication(
            @RequestBody CreateApplicationRequest request,
            Authentication authentication) {
        Long userId = ((UserPrincipal) Objects.requireNonNull(authentication.getPrincipal())).getUser().getId();
        Application application = applicationService.createApplication(userId, request.getInternshipId());
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

    @GetMapping("/my")
    public List<ApplicationResponseDTO> getMyApplications(Authentication authentication) {
        Long userId = ((UserPrincipal) Objects.requireNonNull(authentication.getPrincipal())).getUser().getId();
        return applicationService.getApplicationsByUserId(userId)
                .stream()
                .map(applicationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/company-grouped")
    public List<InternshipApplicationsGroupDTO> getGroupedView(Authentication authentication) {
        Long userId = ((UserPrincipal) authentication.getPrincipal()).getUser().getId();
        return applicationService.getGroupedApplicationsForCompany(userId);
    }
}
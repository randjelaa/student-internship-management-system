package com.example.internships.controller;

import com.example.internships.dto.internship.*;
import com.example.internships.security.UserPrincipal;
import com.example.internships.service.InternshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/internships")
@RequiredArgsConstructor
public class InternshipController {

    private final InternshipService internshipService;

    @GetMapping
    public Page<InternshipSummaryDTO> getAllInternships(Pageable pageable) {
        return internshipService.getAllInternships(pageable);
    }

    @GetMapping("/{id}")
    public InternshipResponseDTO getById(@PathVariable Long id) {
        return internshipService.getInternshipById(id);
    }

    @GetMapping("/my")
    public List<InternshipResponseDTO> getByCompanyId(Authentication authentication) {
        Long userId = ((UserPrincipal) Objects.requireNonNull(authentication.getPrincipal())).getUser().getId();
        return internshipService.getByCompanyId(userId);
    }

    @PostMapping
    public InternshipResponseDTO create(
            @RequestBody CreateInternshipRequest request,
            Authentication authentication
    ) {
        Long userId = ((UserPrincipal) Objects.requireNonNull(authentication.getPrincipal())).getUser().getId();
        return internshipService.createInternship(request, userId);
    }

    @PutMapping("/{id}")
    public InternshipResponseDTO update(
            @PathVariable Long id,
            @RequestBody UpdateInternshipRequest request,
            Authentication authentication
    ) {
        Long userId = ((UserPrincipal) Objects.requireNonNull(authentication.getPrincipal())).getUser().getId();
        return internshipService.updateInternship(id, request, userId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        internshipService.deleteInternship(id);
    }

    @GetMapping("/not-accepted")
    public Page<InternshipSummaryDTO> getNotAccepted(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(required = false) Long company,
            @RequestParam(required = false) Long tech,
            Pageable pageable,
            Authentication authentication) {
        Long userId = ((UserPrincipal) Objects.requireNonNull(authentication.getPrincipal())).getUser().getId();
        return internshipService.getNotAcceptedInternships(search, company, tech, pageable, userId);
    }

    @GetMapping("/accepted")
    public Page<InternshipSummaryDTO> getAccepted(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(required = false) Long company,
            @RequestParam(required = false) Long tech,
            Pageable pageable,
            Authentication authentication) {
        Long userId = ((UserPrincipal) Objects.requireNonNull(authentication.getPrincipal())).getUser().getId();
        return internshipService.getAcceptedInternships(search, company, tech, pageable, userId);
    }
}
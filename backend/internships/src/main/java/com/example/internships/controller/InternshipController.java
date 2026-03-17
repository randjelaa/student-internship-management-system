package com.example.internships.controller;

import com.example.internships.dto.internship.*;
import com.example.internships.service.InternshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/internships")
@RequiredArgsConstructor
public class InternshipController {

    private final InternshipService internshipService;

    @GetMapping
    public List<InternshipSummaryDTO> getAll(@RequestParam(required = false) Long company,
                                             @RequestParam(required = false) Long technology) {
        if (company != null) return internshipService.filterByCompany(company);
        if (technology != null) return internshipService.filterByTechnology(technology);
        return internshipService.getAllInternships();
    }

    @GetMapping("/{id}")
    public InternshipResponseDTO getById(@PathVariable Long id) {
        return internshipService.getInternshipById(id);
    }

    @PostMapping
    public InternshipResponseDTO create(@RequestBody CreateInternshipRequest request) {
        return internshipService.createInternship(request);
    }

    @PutMapping("/{id}")
    public InternshipResponseDTO update(@PathVariable Long id,
                                        @RequestBody UpdateInternshipRequest request) {
        return internshipService.updateInternship(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        internshipService.deleteInternship(id);
    }
}
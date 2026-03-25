package com.example.internships.controller;

import com.example.internships.dto.internship.*;
import com.example.internships.service.InternshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/internships")
@RequiredArgsConstructor
public class InternshipController {

    private final InternshipService internshipService;

    @GetMapping
    public Page<InternshipSummaryDTO> getAll(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(required = false) Long company,
            @RequestParam(required = false) Long technology,
            Pageable pageable) {

        return internshipService.getInternships(search, company, technology, pageable);
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
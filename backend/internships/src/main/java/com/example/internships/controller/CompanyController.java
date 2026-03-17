package com.example.internships.controller;

import com.example.internships.dto.company.*;
import com.example.internships.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping
    public List<CompanySummaryDTO> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @GetMapping("/{id}")
    public CompanyResponseDTO getCompany(@PathVariable Long id) {
        return companyService.getCompanyById(id);
    }

    @PostMapping
    public CompanyResponseDTO createCompany(@RequestBody CreateCompanyRequest request) {
        return companyService.createCompany(request);
    }

    @PutMapping("/{id}")
    public CompanyResponseDTO updateCompany(@PathVariable Long id,
                                            @RequestBody UpdateCompanyRequest request) {
        return companyService.updateCompany(id, request);
    }

    @PatchMapping("/{id}/activate")
    public CompanyResponseDTO activateCompany(@PathVariable Long id) {
        return companyService.activateCompany(id);
    }

    @PatchMapping("/{id}/deactivate")
    public CompanyResponseDTO deactivateCompany(@PathVariable Long id) {
        return companyService.deactivateCompany(id);
    }

    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
    }
}
package com.example.internships.service;

import com.example.internships.dto.internship.*;
import com.example.internships.entity.Company;
import com.example.internships.entity.Internship;
import com.example.internships.entity.Technology;
import com.example.internships.mapper.InternshipMapper;
import com.example.internships.repository.CompanyRepository;
import com.example.internships.repository.InternshipRepository;
import com.example.internships.repository.TechnologyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class InternshipService {

    private final InternshipRepository internshipRepository;
    private final CompanyRepository companyRepository;
    private final TechnologyRepository technologyRepository;
    private final InternshipMapper internshipMapper;

    public List<InternshipSummaryDTO> getAllInternships() {
        return internshipRepository.findAll()
                .stream()
                .map(internshipMapper::toSummary)
                .collect(Collectors.toList());
    }

    public InternshipResponseDTO getInternshipById(Long id) {
        Internship internship = internshipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Internship not found"));
        return internshipMapper.toResponse(internship);
    }

    public InternshipResponseDTO createInternship(CreateInternshipRequest request) {
        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        Internship internship = internshipMapper.toEntity(request);
        internship.setCompany(company);

        if (request.getTechnologyIds() != null) {
            Set<Technology> technologies = new HashSet<>(technologyRepository.findAllById(request.getTechnologyIds()));
            internship.setTechnologies(technologies);
        }

        Internship saved = internshipRepository.save(internship);
        return internshipMapper.toResponse(saved);
    }

    public InternshipResponseDTO updateInternship(Long id, UpdateInternshipRequest request) {
        Internship internship = internshipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Internship not found"));

        if (request.getCompanyId() != null) {
            Company company = companyRepository.findById(request.getCompanyId())
                    .orElseThrow(() -> new RuntimeException("Company not found"));
            internship.setCompany(company);
        }

        internshipMapper.updateFromDto(request, internship);

        if (request.getTechnologyIds() != null) {
            Set<Technology> technologies = new HashSet<>(technologyRepository.findAllById(request.getTechnologyIds()));
            internship.setTechnologies(technologies);
        }

        Internship updated = internshipRepository.save(internship);
        return internshipMapper.toResponse(updated);
    }

    public void deleteInternship(Long id) {
        Internship internship = internshipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Internship not found"));
        internshipRepository.delete(internship);
    }

    public List<InternshipSummaryDTO> filterByCompany(Long companyId) {
        return internshipRepository.findAll()
                .stream()
                .filter(i -> i.getCompany().getId().equals(companyId))
                .map(internshipMapper::toSummary)
                .collect(Collectors.toList());
    }

    public List<InternshipSummaryDTO> filterByTechnology(Long technologyId) {
        return internshipRepository.findAll()
                .stream()
                .filter(i -> i.getTechnologies().stream().anyMatch(t -> t.getId().equals(technologyId)))
                .map(internshipMapper::toSummary)
                .collect(Collectors.toList());
    }
}
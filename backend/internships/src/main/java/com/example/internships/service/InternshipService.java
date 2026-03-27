package com.example.internships.service;

import com.example.internships.dto.internship.*;
import com.example.internships.entity.Company;
import com.example.internships.entity.Internship;
import com.example.internships.entity.Technology;
import com.example.internships.mapper.InternshipMapper;
import com.example.internships.repository.CompanyRepository;
import com.example.internships.repository.InternshipRepository;
import com.example.internships.repository.StudentRepository;
import com.example.internships.repository.TechnologyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class InternshipService {

    private final InternshipRepository internshipRepository;
    private final CompanyRepository companyRepository;
    private final TechnologyRepository technologyRepository;
    private final InternshipMapper internshipMapper;
    private final StudentRepository studentRepository;

    public InternshipResponseDTO getInternshipById(Long id) {
        Internship internship = internshipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Internship not found"));
        return internshipMapper.toResponse(internship);
    }

    @Transactional
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

    @Transactional
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

    @Transactional
    public void deleteInternship(Long id) {
        Internship internship = internshipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Internship not found"));
        internshipRepository.delete(internship);
    }

    public Page<InternshipSummaryDTO> getNotAcceptedInternships(String title, Long companyId, Long techId, Pageable pageable, Long userId) {
        Long studentId = studentRepository.findByUserId(userId).orElseThrow().getId();
        return internshipRepository.findNotAccepted(studentId, title, companyId, techId, pageable)
                .map(internshipMapper::toSummary);
    }

    public Page<InternshipSummaryDTO> getAcceptedInternships(String title, Long companyId, Long techId, Pageable pageable, Long userId) {
        Long studentId = studentRepository.findByUserId(userId).orElseThrow().getId();
        return internshipRepository.findAcceptedByStudent(studentId, title, companyId, techId, pageable)
                .map(internshipMapper::toSummary);
    }
}
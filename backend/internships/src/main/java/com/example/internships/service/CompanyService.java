package com.example.internships.service;

import com.example.internships.dto.company.*;
import com.example.internships.entity.Company;
import com.example.internships.entity.User;
import com.example.internships.mapper.CompanyMapper;
import com.example.internships.repository.CompanyRepository;
import com.example.internships.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final CompanyMapper companyMapper;
    private final PasswordEncoder passwordEncoder;

    public List<CompanySummaryDTO> getAllCompanies() {
        return companyRepository.findAll()
                .stream()
                .map(companyMapper::toSummary)
                .toList();
    }

    public CompanyResponseDTO getCompanyById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + id));
        return companyMapper.toResponse(company);
    }

    public CompanyResponseDTO createCompany(CreateCompanyRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("ROLE_COMPANY");
        user.setActive(true);

        User savedUser = userRepository.save(user);

        Company company = companyMapper.toEntity(request);
        company.setUser(savedUser);
        company.setActive(true);

        Company saved = companyRepository.save(company);

        return companyMapper.toResponse(saved);
    }

    public CompanyResponseDTO updateCompany(Long id, UpdateCompanyRequest request) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + id));

        User user = company.getUser();

        if (request.getEmail() != null) {
            userRepository.findByEmail(request.getEmail())
                    .filter(existing -> !existing.getId().equals(user.getId()))
                    .ifPresent(u -> { throw new RuntimeException("Email already exists"); });
            user.setEmail(request.getEmail());
        }

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        companyMapper.updateFromDto(request, company);
        Company updated = companyRepository.save(company);

        return companyMapper.toResponse(updated);
    }

    public CompanyResponseDTO activateCompany(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + id));
        company.setActive(true);
        return companyMapper.toResponse(companyRepository.save(company));
    }

    public CompanyResponseDTO deactivateCompany(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + id));
        company.setActive(false);
        return companyMapper.toResponse(companyRepository.save(company));
    }

    public void deleteCompany(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + id));
        companyRepository.delete(company);
    }
}
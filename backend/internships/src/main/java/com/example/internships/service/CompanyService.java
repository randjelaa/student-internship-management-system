package com.example.internships.service;

import com.example.internships.dto.company.*;
import com.example.internships.dto.enums.Role;
import com.example.internships.entity.Company;
import com.example.internships.entity.User;
import com.example.internships.mapper.CompanyMapper;
import com.example.internships.repository.CompanyRepository;
import com.example.internships.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final UserRepository userRepository;
    private final UserService userService;

    public List<CompanySummaryDTO> getAllCompanies() {
        return companyRepository.findAll()
                .stream()
                .map(companyMapper::toSummary)
                .toList();
    }

    public CompanyResponseDTO getCompanyById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        return companyMapper.toResponse(company);
    }

    @Transactional
    public CompanyResponseDTO createCompany(CreateCompanyRequest request) {
        User user = userService.createUser(
                request.getEmail(),
                request.getPassword(),
                Role.ROLE_COMPANY
        );

        Company company = companyMapper.toEntity(request);
        company.setUser(user);

        return companyMapper.toResponse(companyRepository.save(company));
    }

    @Transactional
    public CompanyResponseDTO updateCompany(Long id, UpdateCompanyRequest request) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        User user = company.getUser();

        if (request.getEmail() != null) {
            userService.validateEmailUnique(request.getEmail(), user.getId());
            user.setEmail(request.getEmail());
        }

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            userService.updatePassword(user, request.getPassword());
        }

        companyMapper.updateFromDto(request, company);

        return companyMapper.toResponse(companyRepository.save(company));
    }

    @Transactional
    public CompanyResponseDTO activateCompany(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        company.getUser().setActive(true);
        return companyMapper.toResponse(companyRepository.save(company));
    }

    @Transactional
    public CompanyResponseDTO deactivateCompany(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        company.getUser().setActive(false);
        return companyMapper.toResponse(companyRepository.save(company));
    }

    @Transactional
    public void deleteCompany(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        companyRepository.delete(company);
        userRepository.delete(company.getUser());
    }
}
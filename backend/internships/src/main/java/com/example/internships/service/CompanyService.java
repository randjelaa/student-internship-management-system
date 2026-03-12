package com.example.internships.service;

import com.example.internships.entity.Company;
import com.example.internships.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company getCompanyById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + id));
    }

    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    public Company updateCompany(Long id, Company updatedCompany) {
        Company existing = getCompanyById(id);

        existing.setUser(updatedCompany.getUser());
        existing.setName(updatedCompany.getName());
        existing.setDescription(updatedCompany.getDescription());
        existing.setWebsite(updatedCompany.getWebsite());
        existing.setActive(updatedCompany.getActive());

        return companyRepository.save(existing);
    }

    public void deleteCompany(Long id) {
        Company company = getCompanyById(id);

        companyRepository.delete(company);
    }
}
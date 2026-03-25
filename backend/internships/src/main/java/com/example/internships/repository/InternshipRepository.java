package com.example.internships.repository;

import com.example.internships.entity.Internship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InternshipRepository extends JpaRepository<Internship, Long> {
    Page<Internship> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<Internship> findByTitleContainingIgnoreCaseAndCompanyId(String title, Long companyId, Pageable pageable);
    Page<Internship> findByTitleContainingIgnoreCaseAndTechnologiesId(String title, Long technologyId, Pageable pageable);
}
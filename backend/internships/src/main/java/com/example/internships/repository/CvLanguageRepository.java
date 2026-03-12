package com.example.internships.repository;

import com.example.internships.entity.CvLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CvLanguageRepository extends JpaRepository<CvLanguage, Long> {
}
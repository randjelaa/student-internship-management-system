package com.example.internships.repository;

import com.example.internships.entity.CvEducation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CvEducationRepository extends JpaRepository<CvEducation, Long> {
}
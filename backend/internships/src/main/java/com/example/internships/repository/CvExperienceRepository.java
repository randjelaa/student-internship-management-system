package com.example.internships.repository;

import com.example.internships.entity.CvExperience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CvExperienceRepository extends JpaRepository<CvExperience, Long> {
}
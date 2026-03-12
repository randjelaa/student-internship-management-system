package com.example.internships.repository;

import com.example.internships.entity.CvSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CvSkillRepository extends JpaRepository<CvSkill, Long> {
}
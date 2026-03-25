package com.example.internships.repository;

import com.example.internships.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    List<Recommendation> findByStudentId(Long studentId);
    void deleteByStudentId(Long studentId);
}
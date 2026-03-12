package com.example.internships.repository;

import com.example.internships.entity.CvInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CvInterestRepository extends JpaRepository<CvInterest, Long> {
}
package com.example.internships.repository;

import com.example.internships.entity.WorkLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkLogRepository extends JpaRepository<WorkLog, Long> {
    Page<WorkLog> findByInternshipIdAndStudentId(Long internshipId, Long studentId, Pageable pageable);
}
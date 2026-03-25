package com.example.internships.repository;

import com.example.internships.entity.WorkLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkLogRepository extends JpaRepository<WorkLog, Long> {
    Page<WorkLog> findByInternshipId(Long internshipId, Pageable pageable);
    Page<WorkLog> findByStudentUserId(Long userId, Pageable pageable);
}
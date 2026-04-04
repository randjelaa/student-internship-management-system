package com.example.internships.repository;

import com.example.internships.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByStudentId(Long studentId);

    @Query("""
    SELECT a FROM Application a
    JOIN FETCH a.student
    JOIN FETCH a.internship i
    WHERE i.company.id = :companyId
""")
    List<Application> findByCompanyId(Long companyId);

    List<Application> findByInternship_Company_Id(Long companyId);
}
package com.example.internships.repository;

import com.example.internships.entity.Internship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InternshipRepository extends JpaRepository<Internship, Long> {
    Page<Internship> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<Internship> findByTitleContainingIgnoreCaseAndCompanyId(String title, Long companyId, Pageable pageable);
    Page<Internship> findByTitleContainingIgnoreCaseAndTechnologiesId(String title, Long technologyId, Pageable pageable);

    @Query("SELECT DISTINCT i FROM Internship i " +
            "LEFT JOIN i.technologies t " +
            "WHERE i.id NOT IN (" +
            "  SELECT a.internship.id FROM Application a " +
            "  WHERE a.student.id = :studentId AND a.status = 'ACCEPTED'" +
            ") " +
            "AND (:title IS NULL OR LOWER(i.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
            "AND (:companyId IS NULL OR i.company.id = :companyId) " +
            "AND (:techId IS NULL OR t.id = :techId)")
    Page<Internship> findNotAccepted(
            @Param("studentId") Long studentId,
            @Param("title") String title,
            @Param("companyId") Long companyId,
            @Param("techId") Long techId,
            Pageable pageable);

    @Query("SELECT DISTINCT i FROM Internship i " +
            "JOIN i.applications a " +
            "LEFT JOIN i.technologies t " +
            "WHERE a.student.id = :studentId AND a.status = 'ACCEPTED' " +
            "AND (:title IS NULL OR LOWER(i.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
            "AND (:companyId IS NULL OR i.company.id = :companyId) " +
            "AND (:techId IS NULL OR t.id = :techId)")
    Page<Internship> findAcceptedByStudent(
            @Param("studentId") Long studentId,
            @Param("title") String title,
            @Param("companyId") Long companyId,
            @Param("techId") Long techId,
            Pageable pageable);
}
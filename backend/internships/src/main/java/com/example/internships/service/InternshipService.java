package com.example.internships.service;

import com.example.internships.entity.Internship;
import com.example.internships.entity.Technology;
import com.example.internships.repository.InternshipRepository;
import com.example.internships.repository.TechnologyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class InternshipService {

    private final InternshipRepository internshipRepository;
    private final TechnologyRepository technologyRepository;

    public List<Internship> getAllInternships() {
        return internshipRepository.findAll();
    }

    public Internship getInternshipById(Long id) {
        return internshipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Internship not found"));
    }

    public Internship createInternship(Internship internship) {
        return internshipRepository.save(internship);
    }

    public Internship updateInternship(Long id, Internship updatedInternship) {
        Internship existing = getInternshipById(id);

        existing.setTitle(updatedInternship.getTitle());
        existing.setDescription(updatedInternship.getDescription());
        existing.setLocation(updatedInternship.getLocation());
        existing.setStartDate(updatedInternship.getStartDate());
        existing.setEndDate(updatedInternship.getEndDate());
        existing.setRequirements(updatedInternship.getRequirements());

        return internshipRepository.save(existing);
    }

    public void deleteInternship(Long id) {
        Internship internship = getInternshipById(id);
        internshipRepository.delete(internship);
    }

    public Internship addTechnologies(Long internshipId, Set<Long> technologyIds) {
        Internship internship = getInternshipById(internshipId);

        Set<Technology> technologies = new HashSet<>(technologyRepository.findAllById(technologyIds));
        internship.getTechnologies().addAll(technologies);

        return internshipRepository.save(internship);
    }
}

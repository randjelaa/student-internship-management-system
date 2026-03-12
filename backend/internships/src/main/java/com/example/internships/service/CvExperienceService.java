package com.example.internships.service;

import com.example.internships.entity.CvExperience;
import com.example.internships.repository.CvExperienceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CvExperienceService {

    private final CvExperienceRepository cvExperienceRepository;

    public List<CvExperience> getAllCvExperiences() {
        return cvExperienceRepository.findAll();
    }

    public CvExperience getCvExperienceById(Long id) {
        return cvExperienceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CvExperience not found with id: " + id));
    }

    public CvExperience createCvExperience(CvExperience cvExperience) {
        return cvExperienceRepository.save(cvExperience);
    }

    public CvExperience updateCvExperience(Long id, CvExperience updatedCvExperience) {
        CvExperience existing = getCvExperienceById(id);

        existing.setCv(updatedCvExperience.getCv());
        existing.setCompanyName(updatedCvExperience.getCompanyName());
        existing.setPosition(updatedCvExperience.getPosition());
        existing.setDescription(updatedCvExperience.getDescription());
        existing.setStartDate(updatedCvExperience.getStartDate());
        existing.setEndDate(updatedCvExperience.getEndDate());

        return cvExperienceRepository.save(existing);
    }

    public void deleteCvExperience(Long id) {
        CvExperience cvExperience = getCvExperienceById(id);

        cvExperienceRepository.delete(cvExperience);
    }
}
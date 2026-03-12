package com.example.internships.service;

import com.example.internships.entity.CvEducation;
import com.example.internships.repository.CvEducationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CvEducationService {

    private final CvEducationRepository cvEducationRepository;

    public List<CvEducation> getAllCvEducations() {
        return cvEducationRepository.findAll();
    }

    public CvEducation getCvEducationById(Long id) {
        return cvEducationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CvEducation not found with id: " + id));
    }

    public CvEducation createCvEducation(CvEducation cvEducation) {
        return cvEducationRepository.save(cvEducation);
    }

    public CvEducation updateCvEducation(Long id, CvEducation updatedCvEducation) {
        CvEducation existing = getCvEducationById(id);

        existing.setCv(updatedCvEducation.getCv());
        existing.setInstitution(updatedCvEducation.getInstitution());
        existing.setDegree(updatedCvEducation.getDegree());
        existing.setFieldOfStudy(updatedCvEducation.getFieldOfStudy());
        existing.setStartYear(updatedCvEducation.getStartYear());
        existing.setEndYear(updatedCvEducation.getEndYear());

        return cvEducationRepository.save(existing);
    }

    public void deleteCvEducation(Long id) {
        CvEducation cvEducation = getCvEducationById(id);

        cvEducationRepository.delete(cvEducation);
    }
}
package com.example.internships.service;

import com.example.internships.entity.CvLanguage;
import com.example.internships.repository.CvLanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CvLanguageService {

    private final CvLanguageRepository cvLanguageRepository;

    public List<CvLanguage> getAllCvLanguages() {
        return cvLanguageRepository.findAll();
    }

    public CvLanguage getCvLanguageById(Long id) {
        return cvLanguageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CvLanguage not found with id: " + id));
    }

    public CvLanguage createCvLanguage(CvLanguage cvLanguage) {
        return cvLanguageRepository.save(cvLanguage);
    }

    public CvLanguage updateCvLanguage(Long id, CvLanguage updatedCvLanguage) {
        CvLanguage existing = getCvLanguageById(id);

        existing.setCv(updatedCvLanguage.getCv());
        existing.setLanguageName(updatedCvLanguage.getLanguageName());
        existing.setLevel(updatedCvLanguage.getLevel());

        return cvLanguageRepository.save(existing);
    }

    public void deleteCvLanguage(Long id) {
        CvLanguage cvLanguage = getCvLanguageById(id);

        cvLanguageRepository.delete(cvLanguage);
    }
}
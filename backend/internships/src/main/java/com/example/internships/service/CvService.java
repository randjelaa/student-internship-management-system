package com.example.internships.service;

import com.example.internships.entity.Cv;
import com.example.internships.repository.CvRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CvService {

    private final CvRepository cvRepository;

    public List<Cv> getAllCvs() {
        return cvRepository.findAll();
    }

    public Cv getCvById(Long id) {
        return cvRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CV not found with id: " + id));
    }

    public Cv createCv(Cv cv) {
        return cvRepository.save(cv);
    }

    public Cv updateCv(Long id, Cv updatedCv) {
        Cv existing = getCvById(id);

        existing.setStudent(updatedCv.getStudent());
        existing.setPhotoUrl(updatedCv.getPhotoUrl());
        existing.setSummary(updatedCv.getSummary());

        return cvRepository.save(existing);
    }

    public void deleteCv(Long id) {
        Cv cv = getCvById(id);

        cvRepository.delete(cv);
    }
}
package com.example.internships.service;

import com.example.internships.entity.CvInterest;
import com.example.internships.repository.CvInterestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CvInterestService {

    private final CvInterestRepository cvInterestRepository;

    public List<CvInterest> getAllCvInterests() {
        return cvInterestRepository.findAll();
    }

    public CvInterest getCvInterestById(Long id) {
        return cvInterestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CvInterest not found with id: " + id));
    }

    public CvInterest createCvInterest(CvInterest cvInterest) {
        return cvInterestRepository.save(cvInterest);
    }

    public CvInterest updateCvInterest(Long id, CvInterest updatedCvInterest) {
        CvInterest existing = getCvInterestById(id);

        existing.setCv(updatedCvInterest.getCv());
        existing.setInterestName(updatedCvInterest.getInterestName());

        return cvInterestRepository.save(existing);
    }

    public void deleteCvInterest(Long id) {
        CvInterest cvInterest = getCvInterestById(id);

        cvInterestRepository.delete(cvInterest);
    }
}
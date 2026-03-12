package com.example.internships.service;

import com.example.internships.entity.Recommendation;
import com.example.internships.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;

    public List<Recommendation> getAllRecommendations() {
        return recommendationRepository.findAll();
    }

    public Recommendation getRecommendationById(Long id) {
        return recommendationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recommendation not found with id: " + id));
    }

    public Recommendation createRecommendation(Recommendation recommendation) {
        return recommendationRepository.save(recommendation);
    }

    public Recommendation updateRecommendation(Long id, Recommendation updatedRecommendation) {
        Recommendation existing = getRecommendationById(id);

        existing.setStudent(updatedRecommendation.getStudent());
        existing.setInternship(updatedRecommendation.getInternship());
        existing.setScore(updatedRecommendation.getScore());
        existing.setExplanation(updatedRecommendation.getExplanation());

        return recommendationRepository.save(existing);
    }

    public void deleteRecommendation(Long id) {
        Recommendation recommendation = getRecommendationById(id);

        recommendationRepository.delete(recommendation);
    }
}
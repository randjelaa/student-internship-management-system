package com.example.internships.service;

import com.example.internships.ai.AiRecommendationService;
import com.example.internships.dto.recommendation.RecommendationItemDTO;
import com.example.internships.dto.recommendation.RecommendationResponseDTO;
import com.example.internships.entity.Cv;
import com.example.internships.entity.Internship;
import com.example.internships.entity.Recommendation;
import com.example.internships.entity.Student;
import com.example.internships.repository.InternshipRepository;
import com.example.internships.repository.RecommendationRepository;
import com.example.internships.repository.StudentRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final StudentRepository studentRepository;
    private final InternshipRepository internshipRepository;
    private final RecommendationRepository recommendationRepository;
    private final AiRecommendationService aiService;

    @Transactional
    public List<RecommendationResponseDTO> generate(Long userId) {
        Student student = studentRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        String cvText = student.getCvs().stream()
                .findFirst()
                .map(cv -> "Summary: " + cv.getSummary())
                .orElse("No CV provided");

        List<Internship> internships = internshipRepository.findAll();

        String internshipsText = IntStream.range(0, internships.size())
                .mapToObj(i -> String.format("[%d] Title: %s, Description: %s",
                        i, internships.get(i).getTitle(), internships.get(i).getDescription()))
                .collect(Collectors.joining("\n"));

        String prompt = aiService.buildPrompt(cvText, internshipsText);
        String aiResponse = aiService.callGemini(prompt);
        List<RecommendationItemDTO> items = parseAiResponse(aiResponse);

        recommendationRepository.deleteByStudentId(student.getId());

        List<Recommendation> toSave = items.stream()
                .filter(item -> item.getInternshipIndex() != null &&
                        item.getInternshipIndex() >= 0 &&
                        item.getInternshipIndex() < internships.size())
                .map(item -> {
                    Recommendation r = new Recommendation();
                    r.setStudent(student);
                    r.setInternship(internships.get(item.getInternshipIndex()));
                    r.setScore(BigDecimal.valueOf(item.getScore()));
                    r.setExplanation(item.getExplanation());
                    return r;
                })
                .sorted(Comparator.comparing(Recommendation::getScore).reversed())
                .limit(5)
                .collect(Collectors.toList());

        recommendationRepository.saveAll(toSave);

        return toSave.stream().map(this::mapToDTO).toList();
    }

    public List<RecommendationResponseDTO> getByStudent(Long userId) {
        Student student = studentRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return recommendationRepository.findByStudentId(student.getId())
                .stream()
                .sorted(Comparator.comparing(Recommendation::getScore).reversed())
                .map(this::mapToDTO)
                .toList();
    }

    private RecommendationResponseDTO mapToDTO(Recommendation r) {
        RecommendationResponseDTO dto = new RecommendationResponseDTO();
        dto.setInternshipId(r.getInternship().getId());
        dto.setInternshipTitle(r.getInternship().getTitle());
        dto.setScore(r.getScore().doubleValue());
        dto.setExplanation(r.getExplanation());
        return dto;
    }

    private String cleanJson(String response) {
        if (response == null) return "[]";
        return response
                .replace("```json", "")
                .replace("```", "")
                .trim();
    }

    private List<RecommendationItemDTO> parseAiResponse(String aiResponse) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            String cleaned = cleanJson(aiResponse);
            return mapper.readValue(
                    cleaned,
                    new TypeReference<>() {
                    }
            );

        } catch (Exception e) {
            System.out.println("AI parsing failed: " + e.getMessage());
            return List.of();
        }
    }
}
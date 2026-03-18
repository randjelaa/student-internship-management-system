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

    public List<RecommendationResponseDTO> generate(Long studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // 1️⃣ CV
        String cvText = student.getCvs().stream()
                .findFirst()
                .map(Cv::getSummary)
                .orElse("No CV provided");

        // 2️⃣ internships
        List<Internship> internships = internshipRepository.findAll();

        // 🔥 BITNO: pravilno formatiranje
        String internshipsText = IntStream.range(0, internships.size())
                .mapToObj(i -> i + ". " +
                        internships.get(i).getTitle() + " - " +
                        internships.get(i).getDescription())
                .collect(Collectors.joining("\n"));

        // 3️⃣ prompt
        String prompt = aiService.buildPrompt(cvText, internshipsText);

        // 4️⃣ AI call
        String aiResponse = aiService.callGemini(prompt);

        // 5️⃣ parse
        List<RecommendationItemDTO> items = parseAiResponse(aiResponse);

        // 6️⃣ map + filter + sort
        List<Recommendation> saved = items.stream()
                .filter(item ->
                        item.getInternshipIndex() != null &&
                                item.getInternshipIndex() >= 0 &&
                                item.getInternshipIndex() < internships.size()
                )
                .map(item -> {

                    Internship internship =
                            internships.get(item.getInternshipIndex());

                    Recommendation r = new Recommendation();
                    r.setStudent(student);
                    r.setInternship(internship);
                    r.setScore(BigDecimal.valueOf(item.getScore()));
                    r.setExplanation(item.getExplanation());

                    return r;
                })
                .sorted(Comparator.comparing(Recommendation::getScore).reversed())
                .limit(5)
                .toList();

        // 7️⃣ save
        recommendationRepository.saveAll(saved);

        // 8️⃣ response
        return saved.stream()
                .map(r -> {
                    RecommendationResponseDTO dto = new RecommendationResponseDTO();
                    dto.setInternshipId(r.getInternship().getId());
                    dto.setInternshipTitle(r.getInternship().getTitle());
                    dto.setScore(r.getScore().doubleValue());
                    dto.setExplanation(r.getExplanation());
                    return dto;
                })
                .toList();
    }

    // ✅ GET endpoint
    public List<RecommendationResponseDTO> getByStudent(Long studentId) {

        return recommendationRepository.findByStudentId(studentId)
                .stream()
                .sorted(Comparator.comparing(Recommendation::getScore).reversed())
                .map(r -> {
                    RecommendationResponseDTO dto = new RecommendationResponseDTO();
                    dto.setInternshipId(r.getInternship().getId());
                    dto.setInternshipTitle(r.getInternship().getTitle());
                    dto.setScore(r.getScore().doubleValue());
                    dto.setExplanation(r.getExplanation());
                    return dto;
                })
                .toList();
    }

    // ✅ CLEAN JSON
    private String cleanJson(String response) {

        if (response == null) return "[]";

        return response
                .replace("```json", "")
                .replace("```", "")
                .trim();
    }

    // ✅ SAFE PARSE
    private List<RecommendationItemDTO> parseAiResponse(String aiResponse) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            String cleaned = cleanJson(aiResponse);

            return mapper.readValue(
                    cleaned,
                    new TypeReference<List<RecommendationItemDTO>>() {}
            );

        } catch (Exception e) {

            System.out.println("AI parsing failed: " + e.getMessage());

            return List.of();
        }
    }
}
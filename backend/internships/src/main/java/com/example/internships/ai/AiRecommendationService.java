package com.example.internships.ai;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AiRecommendationService {

    private final Client client;

    @Value("${ai.gemini.model}")
    private String model;

    public AiRecommendationService() {
        this.client = new Client();
    }

    public String callGemini(String prompt) {
        GenerateContentResponse response = client.models.generateContent(
                model,
                prompt,
                null
        );

        return response.text();
    }

    public String buildPrompt(String cvText, String internshipsText) {
        return """
        You are an AI that recommends internships.

        Student CV:
        %s

        Internships:
        %s

        TASK:
        For EACH internship return:
        - internshipIndex
        - score (0 to 1)
        - short explanation

        IMPORTANT:
        - Return ONLY valid JSON
        - Do NOT include markdown
        - Do NOT include explanation outside JSON

        FORMAT:
        [
          { "internshipIndex": 0, "score": 0.85, "explanation": "..." }
        ]
        """.formatted(cvText, internshipsText);
    }
}
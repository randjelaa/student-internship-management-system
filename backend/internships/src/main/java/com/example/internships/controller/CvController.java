package com.example.internships.controller;

import com.example.internships.dto.cv.CreateCvRequest;
import com.example.internships.dto.cv.CvResponseDTO;
import com.example.internships.service.CvService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api/students/{studentId}/cv")
@RequiredArgsConstructor
public class CvController {

    private final CvService cvService;

    @GetMapping
    public CvResponseDTO getCv(@PathVariable Long studentId) {
        return cvService.getCvByStudentId(studentId);
    }

    @PostMapping
    public CvResponseDTO createCv(@PathVariable Long studentId,
                       @RequestBody CreateCvRequest request) {
        return cvService.createCv(studentId, request);
    }

    @PutMapping
    public CvResponseDTO updateCv(@PathVariable Long studentId,
                       @RequestBody CreateCvRequest request) {
        return cvService.updateCv(studentId, request);
    }

    @DeleteMapping
    public void deleteCv(@PathVariable Long studentId) {
        cvService.deleteCv(studentId);
    }

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> downloadCvPdf(@PathVariable Long studentId) {
        byte[] pdfBytes = cvService.generateCvPdf(studentId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=cv_" + studentId + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }

    @PostMapping("/upload-image")
    public ResponseEntity<String> uploadImage(
            @PathVariable Long studentId,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            Path uploadPath = Paths.get("uploads/");
            Files.createDirectories(uploadPath);

            Path filePath = uploadPath.resolve(fileName);
            Files.write(filePath, file.getBytes());

            String fileUrl = "http://localhost:8080/uploads/" + fileName;

            return ResponseEntity.ok(fileUrl);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Upload failed");
        }
    }
}

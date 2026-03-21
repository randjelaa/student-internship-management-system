package com.example.internships.controller;

import com.example.internships.dto.cv.CreateCvRequest;
import com.example.internships.dto.cv.CvResponseDTO;
import com.example.internships.security.UserPrincipal;
import com.example.internships.service.CvService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/api/cv")
@RequiredArgsConstructor
public class CvController {

    private final CvService cvService;

    @Value("${app.upload.dir}")
    private String uploadDir;

    @Value("${app.upload.base-url}")
    private String uploadBaseUrl;

    @GetMapping
    public CvResponseDTO getCv(Authentication authentication) {
        Long userId = ((UserPrincipal) Objects.requireNonNull(authentication.getPrincipal())).getUser().getId();
        return cvService.getCvByUserId(userId);
    }

    @PostMapping
    public CvResponseDTO createCv(
            Authentication authentication,
            @RequestBody CreateCvRequest request
    ) {
        Long userId = ((UserPrincipal) Objects.requireNonNull(authentication.getPrincipal())).getUser().getId();
        return cvService.createCv(userId, request);
    }

    @PutMapping
    public CvResponseDTO updateCv(
            Authentication authentication,
            @RequestBody CreateCvRequest request
    ) {
        Long userId = ((UserPrincipal) Objects.requireNonNull(authentication.getPrincipal())).getUser().getId();
        return cvService.updateCv(userId, request);
    }

    @DeleteMapping
    public void deleteCv(Authentication authentication) {
        Long userId = ((UserPrincipal) Objects.requireNonNull(authentication.getPrincipal())).getUser().getId();
        cvService.deleteCv(userId);
    }

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> downloadCvPdf(Authentication authentication) {
        Long userId = ((UserPrincipal) Objects.requireNonNull(authentication.getPrincipal())).getUser().getId();
        byte[] pdfBytes = cvService.generateCvPdf(userId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=cv_" + userId + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }

    @PostMapping("/upload-image")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty");
            }

            if (!Objects.requireNonNull(file.getContentType()).startsWith("image/")) {
                return ResponseEntity.badRequest().body("Only images allowed");
            }

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            Path uploadPath = Paths.get(uploadDir);
            Files.createDirectories(uploadPath);

            Path filePath = uploadPath.resolve(fileName);
            Files.write(filePath, file.getBytes());

            String fileUrl = uploadBaseUrl + fileName;

            return ResponseEntity.ok(fileUrl);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Upload failed");
        }
    }
}

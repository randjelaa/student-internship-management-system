package com.example.internships.controller;

import com.example.internships.dto.worklog.*;
import com.example.internships.security.UserPrincipal;
import com.example.internships.service.WorkLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/worklogs")
@RequiredArgsConstructor
public class WorkLogController {

    private final WorkLogService workLogService;

    @GetMapping("/my/internship/{internshipId}")
    public ResponseEntity<Page<WorkLogResponseDTO>> getMyWorkLogsByInternship(
            @PathVariable Long internshipId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication
    ) {
        Long userId = ((UserPrincipal) Objects.requireNonNull(authentication.getPrincipal())).getUser().getId();
        return ResponseEntity.ok(workLogService.getMyWorkLogsByInternship(internshipId, userId, page, size));
    }

    @PostMapping
    public ResponseEntity<WorkLogResponseDTO> createWorkLog(
            @RequestBody CreateWorkLogRequest request,
            Authentication authentication
    ) {
        Long userId = ((UserPrincipal) Objects.requireNonNull(authentication.getPrincipal())).getUser().getId();
        return ResponseEntity.ok(workLogService.createWorkLog(request, userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkLogResponseDTO> updateWorkLog(
            @PathVariable Long id,
            @RequestBody UpdateWorkLogRequest request,
            Authentication authentication
    ) {
        Long userId = ((UserPrincipal) Objects.requireNonNull(authentication.getPrincipal())).getUser().getId();
        return ResponseEntity.ok(workLogService.updateWorkLog(id, request, userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkLog(
            @PathVariable Long id,
            Authentication authentication
    ) {
        Long userId = ((UserPrincipal) Objects.requireNonNull(authentication.getPrincipal())).getUser().getId();
        workLogService.deleteWorkLog(id, userId);
        return ResponseEntity.noContent().build();
    }
}
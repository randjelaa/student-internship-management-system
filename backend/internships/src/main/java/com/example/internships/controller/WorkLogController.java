package com.example.internships.controller;

import com.example.internships.dto.worklog.*;
import com.example.internships.security.UserPrincipal;
import com.example.internships.service.WorkLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/worklogs")
@RequiredArgsConstructor
public class WorkLogController {

    private final WorkLogService workLogService;

    @GetMapping("/internship/{internshipId}")
    public ResponseEntity<Page<WorkLogResponseDTO>> getWorkLogsByInternship(
            @PathVariable Long internshipId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(workLogService.getWorkLogsByInternship(internshipId, page, size));
    }

    @PostMapping
    public ResponseEntity<WorkLogResponseDTO> createWorkLog(
            @RequestBody CreateWorkLogRequest request,
            Authentication authentication
    ) {
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        return ResponseEntity.ok(workLogService.createWorkLog(request, principal.getUser().getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkLogResponseDTO> updateWorkLog(
            @PathVariable Long id,
            @RequestBody UpdateWorkLogRequest request
    ) {
        return ResponseEntity.ok(workLogService.updateWorkLog(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkLog(@PathVariable Long id) {
        workLogService.deleteWorkLog(id);
        return ResponseEntity.noContent().build();
    }
}
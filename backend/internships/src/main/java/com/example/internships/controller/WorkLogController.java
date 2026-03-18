package com.example.internships.controller;

import com.example.internships.dto.worklog.*;
import com.example.internships.service.WorkLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/worklogs")
@RequiredArgsConstructor
public class WorkLogController {

    private final WorkLogService workLogService;

    @GetMapping
    public ResponseEntity<List<WorkLogResponseDTO>> getAllWorkLogs() {
        List<WorkLogResponseDTO> workLogs = workLogService.getAllWorkLogs();
        return ResponseEntity.ok(workLogs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkLogResponseDTO> getWorkLogById(@PathVariable Long id) {
        WorkLogResponseDTO workLog = workLogService.getWorkLogById(id);
        return ResponseEntity.ok(workLog);
    }

    @PostMapping
    public ResponseEntity<WorkLogResponseDTO> createWorkLog(@RequestBody CreateWorkLogRequest request) {
        WorkLogResponseDTO created = workLogService.createWorkLog(request);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkLogResponseDTO> updateWorkLog(
            @PathVariable Long id,
            @RequestBody UpdateWorkLogRequest request
    ) {
        WorkLogResponseDTO updated = workLogService.updateWorkLog(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkLog(@PathVariable Long id) {
        workLogService.deleteWorkLog(id);
        return ResponseEntity.noContent().build();
    }
}
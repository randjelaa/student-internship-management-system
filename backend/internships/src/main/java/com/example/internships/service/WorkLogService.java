package com.example.internships.service;

import com.example.internships.dto.worklog.*;
import com.example.internships.entity.Student;
import com.example.internships.entity.WorkLog;
import com.example.internships.entity.Internship;
import com.example.internships.mapper.WorkLogMapper;
import com.example.internships.repository.StudentRepository;
import com.example.internships.repository.WorkLogRepository;
import com.example.internships.repository.InternshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkLogService {

    private final WorkLogRepository workLogRepository;
    private final StudentRepository studentRepository;
    private final InternshipRepository internshipRepository;
    private final WorkLogMapper workLogMapper;

    public List<WorkLogResponseDTO> getAllWorkLogs() {
        return workLogRepository.findAll()
                .stream()
                .map(workLogMapper::toResponse)
                .toList();
    }

    public WorkLogResponseDTO getWorkLogById(Long id) {
        WorkLog workLog = workLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("WorkLog not found"));
        return workLogMapper.toResponse(workLog);
    }

    public WorkLogResponseDTO createWorkLog(CreateWorkLogRequest request, Long userId) {
        Student student = studentRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Internship internship = internshipRepository.findById(request.getInternshipId())
                .orElseThrow(() -> new RuntimeException("Internship not found"));

        WorkLog workLog = workLogMapper.toEntity(request);
        workLog.setStudent(student);
        workLog.setInternship(internship);

        WorkLog saved = workLogRepository.save(workLog);
        return workLogMapper.toResponse(saved);
    }

    public WorkLogResponseDTO updateWorkLog(Long id, UpdateWorkLogRequest request) {
        WorkLog workLog = workLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("WorkLog not found"));

        workLogMapper.updateWorkLogFromDto(request, workLog);
        WorkLog updated = workLogRepository.save(workLog);
        return workLogMapper.toResponse(updated);
    }

    public void deleteWorkLog(Long id) {
        if (!workLogRepository.existsById(id)) {
            throw new RuntimeException("WorkLog not found");
        }
        workLogRepository.deleteById(id);
    }

    public List<WorkLogResponseDTO> getMyWorkLogs(Long userId) {
        Student student = studentRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return workLogRepository.findByStudentId(student.getId())
                .stream()
                .map(workLogMapper::toResponse)
                .toList();
    }
}
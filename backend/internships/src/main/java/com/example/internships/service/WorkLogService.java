package com.example.internships.service;

import com.example.internships.dto.worklog.*;
import com.example.internships.entity.Student;
import com.example.internships.entity.WorkLog;
import com.example.internships.entity.Internship;
import com.example.internships.mapper.WorkLogMapper;
import com.example.internships.repository.StudentRepository;
import com.example.internships.repository.WorkLogRepository;
import com.example.internships.repository.InternshipRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

    @Transactional
    public WorkLogResponseDTO createWorkLog(CreateWorkLogRequest request, Long userId) {
        Student student = studentRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Internship internship = internshipRepository.findById(request.getInternshipId())
                .orElseThrow(() -> new RuntimeException("Internship not found"));

        WorkLog workLog = workLogMapper.toEntity(request);
        workLog.setStudent(student);
        workLog.setInternship(internship);

        return workLogMapper.toResponse(workLogRepository.save(workLog));
    }

    @Transactional
    public WorkLogResponseDTO updateWorkLog(Long id, UpdateWorkLogRequest request, Long userId) {
        Student student = studentRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        WorkLog workLog = workLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("WorkLog not found"));

        if (!Objects.equals(workLog.getStudent().getId(), student.getId())) {
            throw new RuntimeException("Student id mismatch");
        }

        workLogMapper.updateWorkLogFromDto(request, workLog);
        WorkLog updated = workLogRepository.save(workLog);
        return workLogMapper.toResponse(updated);
    }

    @Transactional
    public void deleteWorkLog(Long id, Long userId) {
        Student student = studentRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        WorkLog workLog = workLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("WorkLog not found"));

        if (!Objects.equals(workLog.getStudent().getId(), student.getId())) {
            throw new RuntimeException("Student id mismatch");
        }

        workLogRepository.delete(workLog);
    }

    public Page<WorkLogResponseDTO> getMyWorkLogsByInternship(Long internshipId, Long userId, int page, int size) {
        Student student = studentRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Pageable pageable = PageRequest.of(page, size, Sort.by("startDate").descending());

        return workLogRepository
                .findByInternshipIdAndStudentId(internshipId, student.getId(), pageable)
                .map(workLogMapper::toResponse);
    }
}
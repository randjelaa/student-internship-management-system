package com.example.internships.service;

import com.example.internships.entity.WorkLog;
import com.example.internships.repository.WorkLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkLogService {

    private final WorkLogRepository workLogRepository;

    public List<WorkLog> getAllWorkLogs() {
        return workLogRepository.findAll();
    }

    public WorkLog getWorkLogById(Long id) {
        return workLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("WorkLog not found with id: " + id));
    }

    public WorkLog createWorkLog(WorkLog workLog) {
        return workLogRepository.save(workLog);
    }

    public WorkLog updateWorkLog(Long id, WorkLog updatedWorkLog) {
        WorkLog existing = getWorkLogById(id);

        existing.setStudent(updatedWorkLog.getStudent());
        existing.setInternship(updatedWorkLog.getInternship());
        existing.setWeekNumber(updatedWorkLog.getWeekNumber());
        existing.setDescription(updatedWorkLog.getDescription());

        return workLogRepository.save(existing);
    }

    public void deleteWorkLog(Long id) {
        WorkLog workLog = getWorkLogById(id);

        workLogRepository.delete(workLog);
    }
}
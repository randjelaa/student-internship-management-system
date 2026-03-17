package com.example.internships.service;

import com.example.internships.dto.student.*;
import com.example.internships.entity.Student;
import com.example.internships.entity.User;
import com.example.internships.mapper.StudentMapper;
import com.example.internships.repository.StudentRepository;
import com.example.internships.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final StudentMapper studentMapper;

    public List<StudentSummaryDTO> getAllStudents() {

        return studentRepository.findAll()
                .stream()
                .map(studentMapper::toSummary)
                .toList();
    }

    public StudentResponseDTO getStudentById(Long id) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return studentMapper.toResponse(student);
    }

    public StudentResponseDTO createStudent(CreateStudentRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Student student = studentMapper.toEntity(request);
        student.setUser(user);
        Student saved = studentRepository.save(student);

        return studentMapper.toResponse(saved);
    }

    public StudentResponseDTO updateStudent(Long id, UpdateStudentRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        studentMapper.updateStudentFromDto(request, student);
        Student updated = studentRepository.save(student);

        return studentMapper.toResponse(updated);
    }

    public void deleteStudent(Long id) {

        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Student not found");
        }

        studentRepository.deleteById(id);
    }
}
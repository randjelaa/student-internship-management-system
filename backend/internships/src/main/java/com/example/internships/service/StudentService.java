package com.example.internships.service;

import com.example.internships.dto.student.*;
import com.example.internships.entity.Student;
import com.example.internships.entity.User;
import com.example.internships.mapper.StudentMapper;
import com.example.internships.repository.StudentRepository;
import com.example.internships.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final StudentMapper studentMapper;
    private final PasswordEncoder passwordEncoder;

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
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("ROLE_STUDENT");
        user.setActive(true);

        User savedUser = userRepository.save(user);
        Student student = studentMapper.toEntity(request);

        student.setUser(savedUser);
        Student saved = studentRepository.save(student);

        return studentMapper.toResponse(saved);
    }

    public StudentResponseDTO updateStudent(Long id, UpdateStudentRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        User user = student.getUser();

        if (request.getEmail() != null) {
            userRepository.findByEmail(request.getEmail())
                    .filter(existing -> !existing.getId().equals(user.getId()))
                    .ifPresent(u -> {
                        throw new RuntimeException("Email already exists");
                    });

            user.setEmail(request.getEmail());
        }

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

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
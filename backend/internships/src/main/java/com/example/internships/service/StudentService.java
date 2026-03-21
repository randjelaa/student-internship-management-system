package com.example.internships.service;

import com.example.internships.dto.enums.Role;
import com.example.internships.dto.student.*;
import com.example.internships.entity.Student;
import com.example.internships.entity.User;
import com.example.internships.mapper.StudentMapper;
import com.example.internships.repository.StudentRepository;
import com.example.internships.repository.UserRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final StudentMapper studentMapper;
    private final UserService userService;

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

    @Transactional
    public StudentResponseDTO createStudent(CreateStudentRequest request) {
        User savedUser = userService.createUser(
                request.getEmail(),
                request.getPassword(),
                Role.ROLE_STUDENT
        );

        Student student = studentMapper.toEntity(request);
        student.setUser(savedUser);

        return studentMapper.toResponse(studentRepository.save(student));
    }

    @Transactional
    public StudentResponseDTO updateStudent(Long id, UpdateStudentRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        User user = student.getUser();

        if (request.getEmail() != null) {
            userService.validateEmailUnique(request.getEmail(), user.getId());
            user.setEmail(request.getEmail());
        }

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            userService.updatePassword(user, request.getPassword());
        }

        studentMapper.updateStudentFromDto(request, student);

        return studentMapper.toResponse(studentRepository.save(student));
    }

    @Transactional
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        studentRepository.delete(student);
        userRepository.delete(student.getUser());
    }

    @Transactional
    public List<StudentSummaryDTO> importStudentsFromCsv(MultipartFile file) {
        List<StudentSummaryDTO> importedStudents = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {

            String[] line;
            boolean firstLine = true;

            while ((line = reader.readNext()) != null) {

                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                if (line.length < 7) {
                    throw new RuntimeException("Invalid CSV format");
                }

                String email = line[0].trim();
                String password = line[1].trim();

                if (userRepository.findByEmail(email).isPresent()) {
                    continue; // skip duplikate
                }

                String firstName = line[2].trim();
                String lastName = line[3].trim();
                String indexNumber = line[4].trim();
                String faculty = line[5].trim();

                Integer yearOfStudy = null;
                try {
                    if (!line[6].isBlank()) {
                        yearOfStudy = Integer.parseInt(line[6]);
                    }
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Invalid yearOfStudy for email: " + email);
                }

                User user = userService.createUser(email, password, Role.ROLE_STUDENT);

                CreateStudentRequest request = new CreateStudentRequest();
                request.setFirstName(firstName);
                request.setLastName(lastName);
                request.setIndexNumber(indexNumber);
                request.setFaculty(faculty);
                request.setYearOfStudy(yearOfStudy);

                Student student = studentMapper.toEntity(request);
                student.setUser(user);

                importedStudents.add(
                        studentMapper.toSummary(studentRepository.save(student))
                );
            }

        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException("Failed to parse CSV file: " + e.getMessage());
        }

        return importedStudents;
    }
}
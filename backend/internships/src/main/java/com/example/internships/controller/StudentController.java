package com.example.internships.controller;

import com.example.internships.dto.student.*;
import com.example.internships.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public List<StudentSummaryDTO> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public StudentResponseDTO getStudent(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @PostMapping
    public StudentResponseDTO createStudent(
            @RequestBody CreateStudentRequest request) {

        return studentService.createStudent(request);
    }

    @PutMapping("/{id}")
    public StudentResponseDTO updateStudent(
            @PathVariable Long id,
            @RequestBody UpdateStudentRequest request) {

        return studentService.updateStudent(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

    @PostMapping("/import-csv")
    public ResponseEntity<List<StudentSummaryDTO>> importStudents(@RequestParam("file") MultipartFile file) {
        List<StudentSummaryDTO> students = studentService.importStudentsFromCsv(file);
        return ResponseEntity.ok(students);
    }
}
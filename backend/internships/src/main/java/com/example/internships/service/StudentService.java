package com.example.internships.service;

import com.example.internships.entity.Student;
import com.example.internships.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student updateStudent(Long id, Student updatedStudent) {
        Student existing = getStudentById(id);

        existing.setUser(updatedStudent.getUser());
        existing.setFirstName(updatedStudent.getFirstName());
        existing.setLastName(updatedStudent.getLastName());
        existing.setIndexNumber(updatedStudent.getIndexNumber());
        existing.setFaculty(updatedStudent.getFaculty());
        existing.setYearOfStudy(updatedStudent.getYearOfStudy());

        return studentRepository.save(existing);
    }

    public void deleteStudent(Long id) {
        Student student = getStudentById(id);

        studentRepository.delete(student);
    }
}
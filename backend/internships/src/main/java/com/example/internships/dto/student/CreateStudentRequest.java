package com.example.internships.dto.student;

import lombok.Data;

@Data
public class CreateStudentRequest {

    private String email;
    private String password;

    private String firstName;
    private String lastName;
    private String indexNumber;
    private String faculty;
    private Integer yearOfStudy;
}
package com.example.internships.dto.student;

import lombok.Data;

@Data
public class CreateStudentRequest {

    private Long userId;

    private String firstName;

    private String lastName;

    private String indexNumber;

    private String faculty;

    private Integer yearOfStudy;
}
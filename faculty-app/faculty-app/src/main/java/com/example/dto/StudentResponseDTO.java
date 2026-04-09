package com.example.dto;

import lombok.Data;

@Data
public class StudentResponseDTO {
    private Long id;
    private Long userId;
    private String email;
    private String firstName;
    private String lastName;
    private String indexNumber;
    private String faculty;
    private Integer yearOfStudy;
}
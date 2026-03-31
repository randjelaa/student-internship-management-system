package com.example.model.dto;

public class StudentResponseDTO {

    private Long id;
    private Long userId;
    private String email;

    private String firstName;
    private String lastName;
    private String indexNumber;
    private String faculty;
    private Integer yearOfStudy;

    public StudentResponseDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getIndexNumber() { return indexNumber; }
    public void setIndexNumber(String indexNumber) { this.indexNumber = indexNumber; }

    public String getFaculty() { return faculty; }
    public void setFaculty(String faculty) { this.faculty = faculty; }

    public Integer getYearOfStudy() { return yearOfStudy; }
    public void setYearOfStudy(Integer yearOfStudy) { this.yearOfStudy = yearOfStudy; }
}
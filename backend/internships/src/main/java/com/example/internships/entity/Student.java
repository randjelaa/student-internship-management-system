package com.example.internships.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "students", schema = "internship_system")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Size(max = 100)
    @Column(name = "first_name", length = 100)
    private String firstName;

    @Size(max = 100)
    @Column(name = "last_name", length = 100)
    private String lastName;

    @Size(max = 50)
    @Column(name = "index_number", length = 50)
    private String indexNumber;

    @Size(max = 255)
    @Column(name = "faculty")
    private String faculty;

    @Column(name = "year_of_study")
    private Integer yearOfStudy;

    @org.hibernate.annotations.CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @OneToMany(mappedBy = "student")
    private Set<Application> applications = new LinkedHashSet<>();

    @OneToMany(mappedBy = "student")
    private Set<Cv> cvs = new LinkedHashSet<>();

    @OneToMany(mappedBy = "student")
    private Set<Grade> grades = new LinkedHashSet<>();

    @OneToMany(mappedBy = "student")
    private Set<Recommendation> recommendations = new LinkedHashSet<>();

    @OneToMany(mappedBy = "student")
    private Set<WorkLog> workLogs = new LinkedHashSet<>();

}
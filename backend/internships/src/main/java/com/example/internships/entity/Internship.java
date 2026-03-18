package com.example.internships.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "internships", schema = "internship_system")
public class Internship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Size(max = 255)
    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "description")
    private String description;

    @Size(max = 255)
    @Column(name = "location")
    private String location;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Lob
    @Column(name = "requirements")
    private String requirements;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @OneToMany(mappedBy = "internship")
    private Set<Application> applications = new LinkedHashSet<>();

    @OneToMany(mappedBy = "internship")
    private Set<Grade> grades = new LinkedHashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "internship_technologies",
            joinColumns = @JoinColumn(name = "internship_id"),
            inverseJoinColumns = @JoinColumn(name = "technology_id")
    )
    private Set<Technology> technologies = new HashSet<>();

    @OneToMany(mappedBy = "internship")
    private Set<Recommendation> recommendations = new LinkedHashSet<>();

    @OneToMany(mappedBy = "internship")
    private Set<WorkLog> workLogs = new LinkedHashSet<>();

    public void addTechnology(Technology technology) {
        technologies.add(technology);
        technology.getInternships().add(this);
    }

    public void removeTechnology(Technology technology) {
        technologies.remove(technology);
        technology.getInternships().remove(this);
    }

}
package com.example.internships.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "cv", schema = "internship_system")
public class Cv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Size(max = 255)
    @Column(name = "photo_url")
    private String photoUrl;

    @Lob
    @Column(name = "summary")
    private String summary;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;

    @OneToMany(mappedBy = "cv")
    private Set<CvEducation> cvEducations = new LinkedHashSet<>();

    @OneToMany(mappedBy = "cv")
    private Set<CvExperience> cvExperiences = new LinkedHashSet<>();

    @OneToMany(mappedBy = "cv")
    private Set<CvInterest> cvInterests = new LinkedHashSet<>();

    @OneToMany(mappedBy = "cv")
    private Set<CvLanguage> cvLanguages = new LinkedHashSet<>();

    @OneToMany(mappedBy = "cv")
    private Set<CvSkill> cvSkills = new LinkedHashSet<>();

}
package com.example.internships.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "education", schema = "internship_system")
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Size(max = 255)
    @Column(name = "institution")
    private String institution;

    @Size(max = 255)
    @Column(name = "degree")
    private String degree;

    @Size(max = 255)
    @Column(name = "field_of_study")
    private String fieldOfStudy;

    @Column(name = "start_year")
    private Integer startYear;

    @Column(name = "end_year")
    private Integer endYear;

    @JsonIgnore
    @ManyToMany(mappedBy = "educations")
    private Set<Cv> cvs = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Education)) return false;
        return id != null && id.equals(((Education) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
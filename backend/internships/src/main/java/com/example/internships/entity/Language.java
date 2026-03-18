package com.example.internships.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "languages", schema = "internship_system")
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Size(max = 100)
    @Column(name = "language_name", length = 100)
    private String languageName;

    @Size(max = 50)
    @Column(name = "level", length = 50)
    private String level;

    @JsonIgnore
    @ManyToMany(mappedBy = "languages")
    private Set<Cv> cvs = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Language)) return false;
        return id != null && id.equals(((Language) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
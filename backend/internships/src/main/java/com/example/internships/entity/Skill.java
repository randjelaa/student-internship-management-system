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
@Table(name = "skills", schema = "internship_system")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Size(max = 255)
    @Column(name = "skill_name")
    private String skillName;

    @Size(max = 50)
    @Column(name = "skill_level", length = 50)
    private String skillLevel;

    @JsonIgnore
    @ManyToMany(mappedBy = "skills")
    private Set<Cv> cvs = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Skill)) return false;
        return id != null && id.equals(((Skill) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
package com.example.internships.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "cv_skills", schema = "internship_system")
public class CvSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "cv_id", nullable = false)
    private Cv cv;

    @Size(max = 255)
    @Column(name = "skill_name")
    private String skillName;

    @Size(max = 50)
    @Column(name = "skill_level", length = 50)
    private String skillLevel;

}
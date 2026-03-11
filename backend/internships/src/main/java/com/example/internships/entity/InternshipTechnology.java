package com.example.internships.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "internship_technologies", schema = "internship_system")
public class InternshipTechnology {
    @EmbeddedId
    private InternshipTechnologyId id;

    @MapsId("internshipId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "internship_id", nullable = false)
    private Internship internship;

    @MapsId("technologyId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "technology_id", nullable = false)
    private Technology technology;

}
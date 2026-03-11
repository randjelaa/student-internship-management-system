package com.example.internships.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class InternshipTechnologyId implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = 2702426532896019363L;
    @NotNull
    @Column(name = "internship_id", nullable = false)
    private Long internshipId;

    @NotNull
    @Column(name = "technology_id", nullable = false)
    private Long technologyId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        InternshipTechnologyId entity = (InternshipTechnologyId) o;
        return Objects.equals(this.internshipId, entity.internshipId) &&
                Objects.equals(this.technologyId, entity.technologyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(internshipId, technologyId);
    }

}
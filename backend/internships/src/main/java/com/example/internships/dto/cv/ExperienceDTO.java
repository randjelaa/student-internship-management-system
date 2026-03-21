package com.example.internships.dto.cv;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ExperienceDTO {
    private Long id;
    private String companyName;
    private String position;
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy") // 👈 OVO JE KLJUČNO
    private LocalDate startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy") // 👈 I OVO
    private LocalDate endDate;
}

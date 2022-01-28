package com.iqs.emma.course.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "academic_level")
@Data
@NoArgsConstructor
public class AcademicLevelModel {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    public AcademicLevelModel(Long id) {
        this.id = id;
    }
}

package com.iqs.emma.course.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "grade_option")
@Data
@NoArgsConstructor
public class GradeOptionModel {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    public GradeOptionModel(Long id) {
        this.id = id;
    }
}

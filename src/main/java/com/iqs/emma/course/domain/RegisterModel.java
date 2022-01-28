package com.iqs.emma.course.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "register")
@Data
@NoArgsConstructor
public class RegisterModel {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentModel studentModel;
    @ManyToOne
    @JoinColumn(name = "academic_level_id")
    private AcademicLevelModel academicLevel;
    @ManyToOne
    @JoinColumn(name = "grade_option_id")
    private GradeOptionModel gradeOption;
    private LocalDateTime creationDate;
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    public RegisterModel(StudentModel studentModel, AcademicLevelModel academicLevel, GradeOptionModel gradeOption,
                         StatusEnum status, LocalDateTime creationDate) {
        this.studentModel = studentModel;
        this.academicLevel = academicLevel;
        this.gradeOption = gradeOption;
        this.status = status;
        this.creationDate = creationDate;
    }
}

package com.iqs.emma.course.dto;

import com.iqs.emma.course.domain.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterCourseDto {

    private Long documentCityId;
    private Long document;
    private String name;
    private String middleName;
    private String lastname;
    private String secondLastname;
    private String email;
    private Long cellphone;
    private LocalDate documentDate;
    private Long academicLevelId;
    private Long gradeOptionLevelId;
    private Double paymentValue;
    private LocalDate paymentDate;


    public StudentModel toStudentModel() {
        return new StudentModel(
                new CityModel(documentCityId),
                document,
                documentDate,
                name,
                middleName,
                lastname,
                secondLastname,
                email,
                cellphone,
                StatusEnum.ACTIVE,
                LocalDateTime.now()
        );
    }

    public RegisterModel toRegisterModel(StudentModel studentModel) {
        return new RegisterModel(
                studentModel,
                new AcademicLevelModel(academicLevelId),
                new GradeOptionModel(gradeOptionLevelId),
                StatusEnum.ACTIVE,
                LocalDateTime.now()
        );
    }

    public PaymentModel toPaymentModel(RegisterModel register) {
        return new PaymentModel(
                register,
                LocalDateTime.now(),
                paymentDate,
                paymentValue,
                StatusEnum.ACTIVE
        );
    }
}

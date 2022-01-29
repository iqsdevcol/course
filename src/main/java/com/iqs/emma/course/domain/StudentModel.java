package com.iqs.emma.course.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "student")
@Data
@NoArgsConstructor
public class StudentModel {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "document_city_id")
    private CityModel documentCity;
    private Long document;
    private LocalDate documentDate;
    private String name;
    private String middleName;
    private String lastname;
    private String secondLastname;
    private String email;
    private Long cellphone;
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
    private LocalDateTime creationDate;

    public StudentModel(CityModel documentCity, Long document, LocalDate documentDate, String name,
                        String middleName, String lastname, String secondLastname, String email, Long cellphone,
                        StatusEnum status, LocalDateTime creationDate) {
        this.documentCity = documentCity;
        this.document = document;
        this.documentDate = documentDate;
        this.name = name;
        this.middleName = middleName;
        this.lastname = lastname;
        this.secondLastname = secondLastname;
        this.email = email;
        this.cellphone = cellphone;
        this.status = status;
        this.creationDate = creationDate;
    }
}

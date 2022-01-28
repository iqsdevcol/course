package com.iqs.emma.course.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "city")
@Data
@NoArgsConstructor
public class CityModel {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "state_id")
    private StateModel state;
    private String name;
    private Long code;
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    public CityModel(Long id) {
        this.id = id;
    }
}

package com.iqs.emma.course.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity(name = "state")
@Data
@NoArgsConstructor
public class StateModel {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Long code;
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @OneToMany
    @JoinColumn(name = "state_id")
    private List<CityModel> cities;

}

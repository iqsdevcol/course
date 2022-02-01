package com.iqs.emma.course.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CityDto {

    private Long id;
    private Long stateId;
    private String stateName;
    private String name;
    private Long code;
    private String status;
}

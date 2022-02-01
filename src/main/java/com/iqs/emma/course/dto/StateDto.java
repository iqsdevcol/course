package com.iqs.emma.course.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor
public class StateDto {


    private Long id;
    private String name;
    private Long code;
    private String status;

    private List<CityDto> cities;

}

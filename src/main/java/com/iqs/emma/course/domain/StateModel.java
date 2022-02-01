package com.iqs.emma.course.domain;

import com.iqs.emma.course.dto.CityDto;
import com.iqs.emma.course.dto.StateDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

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

    public StateDto toStateDtoWithCityList(List<CityModel> cities) {
        List<CityDto> citiesDto = cities.stream().map(CityModel::toCityDto).collect(Collectors.toList());
        return new StateDto(id, name, code, status.name(), citiesDto);
    }
}

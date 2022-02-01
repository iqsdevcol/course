package com.iqs.emma.course.service.impl;

import com.iqs.emma.course.dao.CityDao;
import com.iqs.emma.course.domain.StatusEnum;
import com.iqs.emma.course.dto.StateDto;
import com.iqs.emma.course.service.StateService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StateServiceImpl implements StateService {
    private final CityDao cityDao;

    public StateServiceImpl(CityDao cityDao) {
        this.cityDao = cityDao;
    }

    @Override
    public List<StateDto> getActiveStates() {
        List<StateDto> statesDto = new ArrayList<>();
        cityDao.findByStatusAndStateStatus(StatusEnum.ACTIVE, StatusEnum.ACTIVE).stream()
                .collect(Collectors.groupingBy(cityModel -> cityModel.getState().getName()))
                .forEach((key, value) -> statesDto.add(value.get(0).getState().toStateDtoWithCityList(value)));
        return statesDto;
    }
}

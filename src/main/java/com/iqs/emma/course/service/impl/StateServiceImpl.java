package com.iqs.emma.course.service.impl;

import com.iqs.emma.course.dao.CityDao;
import com.iqs.emma.course.domain.CityModel;
import com.iqs.emma.course.domain.StatusEnum;
import com.iqs.emma.course.service.StateService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StateServiceImpl implements StateService {
    private final CityDao cityDao;

    public StateServiceImpl(CityDao cityDao) {
        this.cityDao = cityDao;
    }

    @Override
    public Map<String, List<CityModel>> getActiveStates() {
        return cityDao.findByStatusAndStateStatus(StatusEnum.ACTIVE, StatusEnum.ACTIVE).stream()
                .collect(Collectors.groupingBy(cityModel -> cityModel.getState().getName()));
    }
}

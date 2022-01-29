package com.iqs.emma.course.service;

import com.iqs.emma.course.domain.CityModel;
import com.iqs.emma.course.domain.StateModel;

import java.util.List;
import java.util.Map;

public interface StateService {
    Map<String, List<CityModel>> getActiveStates();
}

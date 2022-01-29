package com.iqs.emma.course.dao;

import com.iqs.emma.course.domain.CityModel;
import com.iqs.emma.course.domain.StateModel;
import com.iqs.emma.course.domain.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.stream.Stream;

public interface CityDao extends JpaRepository<CityModel, Long> {
    List<CityModel> findByStatusAndStateStatus(StatusEnum status, StatusEnum stateStatus);
}

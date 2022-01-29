package com.iqs.emma.course.dao;

import com.iqs.emma.course.domain.AcademicLevelModel;
import com.iqs.emma.course.domain.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AcademicLevelDao extends JpaRepository<AcademicLevelModel, Long> {
    List<AcademicLevelModel> findByStatus(StatusEnum status);
}

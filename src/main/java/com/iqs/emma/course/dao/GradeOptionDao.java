package com.iqs.emma.course.dao;

import com.iqs.emma.course.domain.GradeOptionModel;
import com.iqs.emma.course.domain.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradeOptionDao extends JpaRepository<GradeOptionModel, Long> {
    List<GradeOptionModel> findByStatus(StatusEnum status);
}

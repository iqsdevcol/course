package com.iqs.emma.course.service;

import com.iqs.emma.course.domain.GradeOptionModel;

import java.util.List;

public interface GradeOptionService {
    List<GradeOptionModel> getActiveGradeOption();
}

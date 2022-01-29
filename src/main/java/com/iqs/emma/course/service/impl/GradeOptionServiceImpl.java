package com.iqs.emma.course.service.impl;

import com.iqs.emma.course.dao.GradeOptionDao;
import com.iqs.emma.course.domain.GradeOptionModel;
import com.iqs.emma.course.domain.StatusEnum;
import com.iqs.emma.course.service.GradeOptionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeOptionServiceImpl implements GradeOptionService {

    private final GradeOptionDao GradeOptionDao;

    public GradeOptionServiceImpl(GradeOptionDao gradeOptionDao) {
        GradeOptionDao = gradeOptionDao;
    }

    @Override
    public List<GradeOptionModel> getActiveGradeOption() {
        return GradeOptionDao.findByStatus(StatusEnum.ACTIVE);
    }
}

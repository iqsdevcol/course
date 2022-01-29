package com.iqs.emma.course.service.impl;

import com.iqs.emma.course.dao.AcademicLevelDao;
import com.iqs.emma.course.domain.AcademicLevelModel;
import com.iqs.emma.course.domain.StatusEnum;
import com.iqs.emma.course.service.AcademicLevelService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcademicLevelServiceImpl implements AcademicLevelService {
    private final AcademicLevelDao academicLevelDao;

    public AcademicLevelServiceImpl(AcademicLevelDao academicLevelDao) {
        this.academicLevelDao = academicLevelDao;
    }

    @Override
    public List<AcademicLevelModel> getActiveAcademicLevels() {
        return academicLevelDao.findByStatus(StatusEnum.ACTIVE);
    }
}

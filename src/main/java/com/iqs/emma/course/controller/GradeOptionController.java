package com.iqs.emma.course.controller;

import com.iqs.emma.course.domain.AcademicLevelModel;
import com.iqs.emma.course.domain.GradeOptionModel;
import com.iqs.emma.course.service.AcademicLevelService;
import com.iqs.emma.course.service.GradeOptionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/grade-options")
@CrossOrigin(origins = "*")
public class GradeOptionController {


    private final GradeOptionService gradeOptionService;

    public GradeOptionController(GradeOptionService gradeOptionService) {
        this.gradeOptionService = gradeOptionService;
    }

    @GetMapping
    @ResponseBody
    public List<GradeOptionModel> getOptionsGrade() {
        return gradeOptionService.getActiveGradeOption();
    }


}

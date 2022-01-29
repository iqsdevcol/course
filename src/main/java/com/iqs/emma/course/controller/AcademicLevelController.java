package com.iqs.emma.course.controller;

import com.iqs.emma.course.domain.AcademicLevelModel;
import com.iqs.emma.course.service.AcademicLevelService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/academic-levels")
@CrossOrigin(origins = "*")
public class AcademicLevelController {


    private final AcademicLevelService academicService;

    public AcademicLevelController(AcademicLevelService academicService) {
        this.academicService = academicService;
    }

    @GetMapping
    @ResponseBody
    public List<AcademicLevelModel> getAcademicLevels() {
        return academicService.getActiveAcademicLevels();
    }


}

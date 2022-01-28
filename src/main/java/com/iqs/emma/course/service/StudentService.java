package com.iqs.emma.course.service;

import com.iqs.emma.course.dto.RegisterCourseDto;

public interface StudentService {
    Long registerCourse(RegisterCourseDto registerCourseDto);

    void validateField(String field, String value);
}

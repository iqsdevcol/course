package com.iqs.emma.course.controller;

import com.iqs.emma.course.dto.RegisterCourseDto;
import com.iqs.emma.course.dto.ResponseDto;
import com.iqs.emma.course.service.PaymentService;
import com.iqs.emma.course.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

@RestController
@RequestMapping(path = "/courses")
public class RegisterCourseController {


    private final StudentService studentService;
    private final PaymentService paymentService;

    public RegisterCourseController(StudentService studentService, PaymentService paymentService) {
        this.studentService = studentService;
        this.paymentService = paymentService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto<Long> saveCourseRegister(@RequestBody RegisterCourseDto registerCourseDto) {
        return new ResponseDto<>(studentService.registerCourse(registerCourseDto));
    }

    @PostMapping(path = "/attached/{payment-id}")
    @ResponseBody
    public void saveAttached(@PathVariable(value = "payment-id") Long paymentId,
                             @RequestParam(value = "files") MultipartFile[] files) {
       paymentService.saveAttachments(Arrays.asList(files), paymentId);
    }

    @GetMapping(path = "/validate-data")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void validate(@RequestParam String field, @RequestParam String value) {
        studentService.validateField(field, value);
    }


}

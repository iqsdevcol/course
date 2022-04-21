package com.iqs.emma.course.service.impl;

import com.iqs.emma.course.dao.PaymentDao;
import com.iqs.emma.course.dao.RegisterDao;
import com.iqs.emma.course.dao.StudentDao;
import com.iqs.emma.course.domain.PaymentModel;
import com.iqs.emma.course.domain.RegisterModel;
import com.iqs.emma.course.domain.StudentModel;
import com.iqs.emma.course.dto.RegisterCourseDto;
import com.iqs.emma.course.service.EmailService;
import com.iqs.emma.course.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentServiceImpl implements StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    private static final String DOCUMENT_FIELD = "document";
    private static final String EMAIL_FIELD = "email";
    private static final String DOCUMENT_ERROR_MESSAGE = "El documento %s ya esta registrado";
    private static final String EMAIL_ERROR_MESSAGE = "El email %s ya esta registrado";
    private static final String MESSAGE_INVALID_FIELD = "No existe validacion para el campo %s";

    private final StudentDao studentDao;
    private final RegisterDao registerDao;
    private final PaymentDao paymentDao;
    private final EmailService emailService;

    public StudentServiceImpl(StudentDao studentDao, RegisterDao registerDao, PaymentDao paymentDao, EmailService emailService) {
        this.studentDao = studentDao;
        this.registerDao = registerDao;
        this.paymentDao = paymentDao;
        this.emailService = emailService;
    }

    @Override
    @Transactional
    public Long registerCourse(RegisterCourseDto registerCourseDto) {
        validDataForm(registerCourseDto);

        StudentModel studentModel = registerCourseDto.toStudentModel();
        StudentModel studentSave = studentDao.save(studentModel);
        RegisterModel register = registerCourseDto.toRegisterModel(studentSave);
        RegisterModel registerSave = registerDao.save(register);
        PaymentModel payment = registerCourseDto.toPaymentModel(registerSave);
        PaymentModel paymentSave = paymentDao.save(payment);

        return paymentSave.getId();
    }

    private void validDataForm(RegisterCourseDto registerCourseDto) {
        validDocument(registerCourseDto.getDocument());
        validEmail(registerCourseDto.getEmail());
    }

    private void validEmail(String email) {
        if (studentDao.findByEmail(email).isPresent()) {
            String message = String.format(EMAIL_ERROR_MESSAGE, email);
            logger.error(message);
            throw new IllegalArgumentException(message);
        }
    }

    private void validDocument(Long document) {
        if (studentDao.findByDocument(document).isPresent()) {
            String message = String.format(DOCUMENT_ERROR_MESSAGE, document);
            logger.error(message);
            throw new IllegalArgumentException(message);
        }
    }

    @Override
    public void validateField(String field, String value) {
        switch (field) {
            case DOCUMENT_FIELD:
                validDocument(Long.valueOf(value));
                break;
            case EMAIL_FIELD:
                validEmail(value);
                break;
            default:
                String message = String.format(MESSAGE_INVALID_FIELD, field);
                throw new IllegalArgumentException(message);
        }

    }
}

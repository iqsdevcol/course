package com.iqs.emma.course.service;

import com.iqs.emma.course.domain.PaymentModel;
import org.springframework.web.multipart.MultipartFile;

public interface EmailService {

    void sendSimpleMessage(String to,
                           String subject,
                           String text);

    void sendRegisterCourseWithAttachment(PaymentModel payment, MultipartFile multipartFile);

    void sendRegisterCourseMethod(PaymentModel payment);
}

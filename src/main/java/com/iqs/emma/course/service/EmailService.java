package com.iqs.emma.course.service;

import com.iqs.emma.course.domain.PaymentModel;

public interface EmailService {

    void sendSimpleMessage(String to,
                           String subject,
                           String text);

    void sendRegisterCourseMethod(PaymentModel payment);
}

package com.iqs.emma.course.service.impl;

import com.iqs.emma.course.dao.PaymentDao;
import com.iqs.emma.course.domain.PaymentModel;
import com.iqs.emma.course.service.AWSS3Client;
import com.iqs.emma.course.service.EmailService;
import com.iqs.emma.course.service.PaymentService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final String ERROR_MESSAGE_NOT_FOUND_PAYMENT = "It wasn't found the payment with the ID: %s";

    private final AWSS3Client aWSS3Client;
    private final PaymentDao paymentDao;
    private final EmailService emailService;

    public PaymentServiceImpl(AWSS3Client aWSS3Client, PaymentDao paymentDao, EmailService emailService) {
        this.aWSS3Client = aWSS3Client;
        this.paymentDao = paymentDao;
        this.emailService = emailService;
    }

    @Override
    public void saveAttachments(List<MultipartFile> files, long paymentId) {
        PaymentModel paymentModel = paymentDao.findById(paymentId).orElseThrow(() ->
                new IllegalArgumentException(String.format(ERROR_MESSAGE_NOT_FOUND_PAYMENT, paymentId))
        );

        Long document = paymentModel.getRegister().getStudentModel().getDocument();
        String path = String.format("student/%s/payment", document);

        files.forEach(file -> {
            String url = aWSS3Client.uploadFile(file, path);
            paymentModel.setUrl(url);
            paymentDao.save(paymentModel);
            emailService.sendRegisterCourseWithAttachment(paymentModel,file);
        });
    }
}

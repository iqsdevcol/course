package com.iqs.emma.course.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PaymentService {

    void saveAttachments(List<MultipartFile> files, long ticketId);


}

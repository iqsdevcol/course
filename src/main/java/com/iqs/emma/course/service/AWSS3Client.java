package com.iqs.emma.course.service;

import org.springframework.web.multipart.MultipartFile;

public interface AWSS3Client {
    String uploadFile(MultipartFile multipartFile, String path);
}

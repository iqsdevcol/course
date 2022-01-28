package com.iqs.emma.course.service.impl;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.iqs.emma.course.service.AWSS3Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class AWSS3ClientImp implements AWSS3Client {

    private static final String FORMAT_GET_FILE = "%s/%d/%s";
    private static final Logger LOGGER = LoggerFactory.getLogger(AWSS3ClientImp.class);

    public AmazonS3 s3client;

    @Value("${amazonProperties.endpointUrl}")
    private String endpointUrl;
    @Value("${amazonProperties.bucketName}")
    private String bucketName;
    @Value("${amazonProperties.accessKey}")
    private String accessKey;
    @Value("${amazonProperties.secretKey}")
    private String secretKey;

    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.s3client = new AmazonS3Client(credentials);
    }

    @Override
    public String uploadFile(MultipartFile multipartFile, String path) {
        try {
            File file = convertMultiPartToFile(multipartFile);
            String name = generateFileName(multipartFile);
            String pathFileName = String.format("%s/%s", path, name);
            uploadFileTos3bucket(pathFileName, file);
            file.delete();
            return String.format("%s/%s/%s", endpointUrl, bucketName, pathFileName);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return multiPart.getOriginalFilename().replace(" ", "_");
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }


    public String getPath(Long id, String fileName, String type) {
        return String.format("%s/%s/%s/%d/%s", endpointUrl, bucketName, type, id, fileName);
    }

    @Async
    public byte[] downloadZipFile(List<String> filesNames, Long id, String type) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
        ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);

        for (String fileName : filesNames) {
            zipOutputStream.putNextEntry(new ZipEntry(fileName));
            InputStream fileInputStream = downloadFile(String.format(FORMAT_GET_FILE, type, id, fileName));

            IOUtils.copy(fileInputStream, zipOutputStream);

            fileInputStream.close();
            zipOutputStream.closeEntry();
        }

        if (zipOutputStream != null) {
            zipOutputStream.finish();
            zipOutputStream.flush();
            IOUtils.closeQuietly(zipOutputStream, null);
        }
        IOUtils.closeQuietly(bufferedOutputStream, null);
        IOUtils.closeQuietly(byteArrayOutputStream, null);
        return byteArrayOutputStream.toByteArray();

    }

    @Async
    private InputStream downloadFile(final String keyName) {
        final S3Object s3Object = s3client.getObject(bucketName, keyName);
        return s3Object.getObjectContent();
    }

    @Async
    public byte[] downloadFile(String fileName, Long id, String type) {
        byte[] content = null;
        final S3Object s3Object = s3client.getObject(bucketName, String.format(FORMAT_GET_FILE, type, id, fileName));
        final S3ObjectInputStream stream = s3Object.getObjectContent();

        try {
            content = IOUtils.toByteArray(stream);
            LOGGER.info("File downloaded successfully.");
            s3Object.close();
        } catch (final IOException ex) {
            LOGGER.info("IO Error Message= " + ex.getMessage());
        }
        return content;
    }
}

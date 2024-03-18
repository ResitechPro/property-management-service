package com.resitechpro.cloud.aws.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Log4j2
public class S3service {

    private final AmazonS3 s3client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.s3.region}")
    private String region;

    public S3service(AmazonS3 s3client) {
        this.s3client = s3client;
    }

    public String uploadFile(String keyName, MultipartFile file) throws IOException {
        String newKeyName = System.currentTimeMillis() + "_" + keyName;
        var putObjectResult = s3client.putObject(bucketName, newKeyName, file.getInputStream(), null);
        log.info(putObjectResult.getMetadata());
        return s3client.getUrl(bucketName, newKeyName).toExternalForm();
    }

    public S3Object getFile(String keyName) {
        return s3client.getObject(bucketName, keyName);
    }
}

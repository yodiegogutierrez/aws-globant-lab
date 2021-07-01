/**
 * This is a lab for Unison Team
 * for new developers.
 * Author: Diego Gutierrez
 * 2021
 **/

package com.project.starter.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
public class S3Service {

    @Value("aws-globant-bucket")
    private String bucketName;
    private String file;
    private S3Client s3Client;

    public S3Service() {
        this.s3Client = S3Client.create();
        this.file = new SimpleDateFormat("yyyyMMddHHmm'.txt'").format(new Date());
    }

    /**
     * Upload file to S3 Bucket
     * @param message
     */
    public void upload(String message) {
        try {
            createFile(message);
            log.info("File '{}' created!", file);
        } catch (IOException e) {
            log.error("Error creating file: '{}', {}", this.file, e.getMessage());
        }

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(file)
                .build();

        Path path = Paths.get(file);

        log.info("Uploading file '{}' to S3 bucket", file);
        this.s3Client.putObject(putObjectRequest, path);
        log.info("File '{}' uploaded!", file);
    }

    /**
     * Create file locally
     * @param message
     * @throws IOException
     */
    private void createFile(String message) throws IOException {
        log.info("Writing value into the file: '{}'", file);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(message);
        writer.close();
    }
}

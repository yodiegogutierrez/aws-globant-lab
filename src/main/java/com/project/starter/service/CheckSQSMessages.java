/**
 * This is a lab for Unison Team
 * for new developers.
 * Author: Diego Gutierrez
 * 2021
 **/

package com.project.starter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.starter.model.SNSPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CheckSQSMessages {

    private SQSService sqsService;
    private S3Service s3Service;
    private ObjectMapper objectMapper;

    public CheckSQSMessages(@Autowired SQSService sqsService, @Autowired S3Service s3Service) {
        this.sqsService = sqsService;
        this.s3Service = s3Service;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Check the queue to find out if there are
     * new messages
     */
    public void observeQueue() {
        List<Message> messages = this.sqsService.invoke();
        if(! messages.isEmpty()) {
            log.info("Messages found!");
            processMessage(messages);
        }
    }

    /**
     * Process the messages list and
     * @param messages
     * @return
     */
    private List<String> processMessage(List<Message> messages) {
        return messages.stream()
                .map(message -> getMessage(message.body()))
                .collect(Collectors.toList());
    }

    /**
     * Gets the body and maps the json string to SNSPayload model
     * @param body
     * @return
     */
    private String getMessage(String body) {
        String message = "";

        try {
            SNSPayload snsPayload = objectMapper.readValue(body, SNSPayload.class);
            message = snsPayload.getMessage();
            log.info("Message: '{}' {}", message, "will be processed and upload to S3");
            s3Service.upload(message);
        } catch (JsonProcessingException e) {
            log.error("Error mapping values {}", e.getMessage());
        }

        return message;
    }
}

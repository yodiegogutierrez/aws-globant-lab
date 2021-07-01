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
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.SqsException;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class SQSService {

    @Value("${aws-globant-queue-url}")
    private String sqsUrl;
    private SqsClient sqsClient;

    public SQSService() {
        this.sqsClient = SqsClient.create();
    }

    /**
     * This method will bring the messages into the queue
     * @return
     */
    public List<Message> invoke() {
        List<Message> messages = Collections.emptyList();

        try{
            ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                    .queueUrl(sqsUrl)
                    .waitTimeSeconds(0)
                    .build();
            messages = this.sqsClient.receiveMessage(receiveMessageRequest).messages();
            deleteMessages(messages);
        } catch (SqsException e) {
            log.error("Error trying to send data: {}", e.getMessage());
        }

        return messages;
    }

    /**
     * Delete message from SQS
     * @param messages
     */
    private void deleteMessages(List<Message> messages) {
        messages.forEach(message -> {
            DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
                    .queueUrl(sqsUrl)
                    .receiptHandle(message.receiptHandle())
                    .build();
            this.sqsClient.deleteMessage(deleteMessageRequest);
        });
    }
}

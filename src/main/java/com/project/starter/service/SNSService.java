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
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.SnsException;

@Service
@Slf4j
public class SNSService {

    @Value("${aws-globant-lab}")
    private String snsArn;
    private SnsClient snsClient;

    public SNSService() {
        this.snsClient = SnsClient.create();
    }

    public String send(String message) {
        String snsResponse = "";

        try{
            PublishRequest publishRequest = PublishRequest.builder()
                    .message(message)
                    .topicArn(snsArn)
                    .build();
            PublishResponse response = snsClient.publish(publishRequest);
            snsResponse = "Message ID: " + response.messageId();
        } catch (SnsException e) {
            log.error("Error trying to send data: {}", e.getMessage());
        }

        return snsResponse;
    }
}

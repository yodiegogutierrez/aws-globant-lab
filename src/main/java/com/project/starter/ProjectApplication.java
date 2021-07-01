package com.project.starter;

import com.project.starter.service.CheckSQSMessages;
import com.project.starter.service.SQSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class ProjectApplication {

	@Autowired
	private CheckSQSMessages checkSQSMessages;

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}

	@Scheduled(fixedRate = 500L)
	void checkSQS() {
		checkSQSMessages.observeQueue();
	}
}

@Configuration
@EnableScheduling
@ConditionalOnProperty(name="scheduling.enabled", matchIfMissing = true)
class SchedulingConfiguration {}
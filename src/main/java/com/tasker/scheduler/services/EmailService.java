package com.tasker.scheduler.services;

import com.common.EmailSendingDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final KafkaTemplate<String, EmailSendingDto> kafkaTemplate;
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public EmailService(KafkaTemplate<String, EmailSendingDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void SendEmail(EmailSendingDto task) {
        logger.debug("Sending EmailSendingDto to kafka : {}", task.getRecipient());
        logger.debug("Email Body: {}", task.getBody());
        kafkaTemplate.send("EMAIL_SENDING_TASKS", task);
        logger.debug("EmailSendingDto sent");
    }
}

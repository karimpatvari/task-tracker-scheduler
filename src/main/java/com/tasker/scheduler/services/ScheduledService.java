package com.tasker.scheduler.services;

import com.common.EmailSendingDto;
import com.tasker.scheduler.models.User;
import com.tasker.scheduler.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduledService {

    private final EmailService emailService;
    private final MessageService messageService;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(ScheduledService.class);

    @Autowired
    public ScheduledService(EmailService emailService, MessageService messageService, UserRepository userRepository) {
        this.emailService = emailService;
        this.messageService = messageService;
        this.userRepository = userRepository;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void SendTasksSummary() {
        logger.debug("Scheduled method SendTasksSummary is called");
        List<User> allUsers = userRepository.findAll();

        allUsers.forEach(user -> {
            EmailSendingDto emailSendingDto = messageService.CreateMessage(user);
            emailService.SendEmail(emailSendingDto);
        });
    }

}

package com.tasker.scheduler.services;

import com.common.EmailSendingDto;
import com.tasker.scheduler.models.Task;
import com.tasker.scheduler.models.User;
import com.tasker.scheduler.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    private final TaskRepository taskRepository;
    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

    @Autowired
    public MessageService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public EmailSendingDto CreateMessage(User user) {
        logger.debug("Creating summary message for user with id: {}", user.getId());
        EmailSendingDto emailSendingDto = new EmailSendingDto();
        emailSendingDto.setRecipient(user.getEmail());
        emailSendingDto.setSubject("Your summary");

        StringBuilder message = new StringBuilder();
        message.append("Hello, ").append(user.getFirstName()).append(" ").append(user.getLastName()).append("! ")
                .append("Here is your task summary:\n");

        List<Task> allTasksByUser = taskRepository.findAllByUser(user);
        List<Task> completed = getCompletedTasks(allTasksByUser);
        List<Task> uncompleted = getUncompletedTasks(allTasksByUser);

        if (!completed.isEmpty()) {
            message.append("You have completed ").append(completed.size()).append(" task(s). Some of those are:\n");
            for (int i = 0; i < Math.min(5, completed.size()); i++) {
                message.append("- ").append(completed.get(i).getTitle()).append("\n");
            }
        }

        if (!uncompleted.isEmpty()) {
            message.append("You still have ").append(uncompleted.size()).append(" task(s) left uncompleted. Some of those are:\n");
            for (int i = 0; i < Math.min(5, uncompleted.size()); i++) {
                message.append("- ").append(uncompleted.get(i).getTitle()).append("\n");
            }
        }

        emailSendingDto.setBody(message.toString());
        return emailSendingDto;
    }


    private List<Task> getUncompletedTasks(List<Task> list) {
        List<Task> uncompletedTasks = new ArrayList<>();
        for (Task task : list) {
            if (!task.getIsCompleted()){
                uncompletedTasks.add(task);
            }
        }
        return uncompletedTasks;
    }

    private List<Task> getCompletedTasks(List<Task> list) {
        List<Task> completedTasks = new ArrayList<>();
        for (Task task : list) {
            if (task.getIsCompleted()){
                completedTasks.add(task);
            }
        }
        return completedTasks;
    }
}

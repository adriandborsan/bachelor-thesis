package com.adriandborsan.adminback.receivers;

import com.adriandborsan.adminback.documents.LogEntry;
import com.adriandborsan.adminback.services.LogEntryService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@RabbitListener(queues = "${spring.rabbitmq.queue}")
@Component
public class LogEntryReceiver {
    private final LogEntryService logEntryService;

    public LogEntryReceiver(LogEntryService logEntryService) {
        this.logEntryService = logEntryService;
    }

    @RabbitHandler
    public void receive(@Payload LogEntry logEntry) {
        Logger.getAnonymousLogger().info("received new log:\t"+logEntry);
        logEntryService.save(logEntry);
    }
}

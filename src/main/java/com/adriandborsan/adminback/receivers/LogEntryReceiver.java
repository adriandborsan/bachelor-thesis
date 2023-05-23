package com.adriandborsan.adminback.receivers;

import com.adriandborsan.adminback.documents.LogEntry;
import com.adriandborsan.adminback.services.LogEntryService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@RabbitListener(queues = "${spring.rabbitmq.queue}")
@Component
public class LogEntryReceiver {
    private final LogEntryService logEntryService;

    public LogEntryReceiver(LogEntryService logEntryService) {
        this.logEntryService = logEntryService;
    }

    @RabbitHandler
    public void receive(@Payload LogEntry logEntry) {
        logEntryService.save(logEntry);
    }
}

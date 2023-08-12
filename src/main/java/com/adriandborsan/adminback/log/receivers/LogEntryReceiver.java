package com.adriandborsan.adminback.log.receivers;

import com.adriandborsan.adminback.log.documents.LogEntry;
import com.adriandborsan.adminback.log.services.LogEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@RabbitListener(queues = "${spring.rabbitmq.queue}")
@Component
@RequiredArgsConstructor
public class LogEntryReceiver {
    private final LogEntryService logEntryService;

    @RabbitHandler
    public void receive(@Payload LogEntry logEntry) {
        logEntryService.save(logEntry);
    }
}

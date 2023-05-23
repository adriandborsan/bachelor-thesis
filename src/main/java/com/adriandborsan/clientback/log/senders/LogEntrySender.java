package com.adriandborsan.clientback.log.senders;

import com.adriandborsan.clientback.log.dao.LogEntry;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class LogEntrySender {
    private final RabbitTemplate rabbitTemplate;

    private final Queue queue;

    public LogEntrySender(RabbitTemplate rabbitTemplate, Queue queue) {
        this.rabbitTemplate = rabbitTemplate;
        this.queue = queue;
    }

    public void send(LogEntry logEntry) {
        rabbitTemplate.convertAndSend(queue.getName(), logEntry);
    }
}

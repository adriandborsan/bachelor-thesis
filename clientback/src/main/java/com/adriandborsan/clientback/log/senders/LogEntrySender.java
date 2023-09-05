package com.adriandborsan.clientback.log.senders;

import com.adriandborsan.clientback.log.dto.LogEntry;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogEntrySender {
    private final RabbitTemplate rabbitTemplate;

    private final Queue queue;

    public void send(LogEntry logEntry) {
        rabbitTemplate.convertAndSend(queue.getName(), logEntry);
    }
}

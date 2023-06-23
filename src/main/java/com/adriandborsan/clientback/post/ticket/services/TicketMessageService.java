package com.adriandborsan.clientback.post.ticket.services;

import com.adriandborsan.clientback.post.ticket.entities.TicketMessage;
import com.adriandborsan.clientback.post.ticket.repositories.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketMessageService {
    private final MessageRepository messageRepository;

    public TicketMessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public TicketMessage createMessage(TicketMessage message) {
        message.setCreatedAt(LocalDateTime.now());
        return messageRepository.save(message);
    }

    public List<TicketMessage> getTicketMessages(Long ticketId) {
        return messageRepository.findByTicketId(ticketId);
    }
}
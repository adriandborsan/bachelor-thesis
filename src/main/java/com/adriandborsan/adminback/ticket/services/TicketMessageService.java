package com.adriandborsan.adminback.ticket.services;

import com.adriandborsan.adminback.ticket.entities.TicketMessage;
import com.adriandborsan.adminback.ticket.repositories.MessageRepository;
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
        return messageRepository.findByTicketIdOrderByCreatedAt(ticketId);
    }
}
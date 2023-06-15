package com.adriandborsan.adminback.ticket.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class TicketMessage {

    @Id
    @GeneratedValue
    private Long messageId;

    private Long ticketId;

    private Long userId;

    private String message;

    private LocalDateTime createdAt;
}

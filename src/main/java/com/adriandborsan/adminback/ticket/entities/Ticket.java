package com.adriandborsan.adminback.ticket.entities;

import com.adriandborsan.adminback.post.entities.Report;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Ticket {

    @Id
    @GeneratedValue
    private Long ticketId;

    private Long clientId;

    private Long postId;

    private Long adminId;

    @Enumerated(EnumType.STRING)
    private Report.ReportStatus status;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}


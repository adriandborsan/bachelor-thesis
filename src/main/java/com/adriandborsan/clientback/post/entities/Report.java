package com.adriandborsan.clientback.post.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Report {
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private ReportStatus status=ReportStatus.PENDING;

    private Long postId;


    private LocalDateTime createdDate;

    public enum ReportStatus {
        PENDING, UNDER_REVIEW, RESOLVED
    }

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
    }
}


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

    //oare nu e mai bine
//    public enum ReportStatus {
//        OPEN,
//        IN_PROGRESS,
//        CLOSED
//    }

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
    }
}


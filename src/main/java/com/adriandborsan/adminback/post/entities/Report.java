package com.adriandborsan.adminback.post.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Report {
    @Id
    @GeneratedValue
    private Long id;
    private String adminId;
    @Enumerated(EnumType.STRING)
    private ReportStatus status;
    private Long postId;
    @CreatedDate
    private LocalDateTime createdDate;
    public enum ReportStatus {
        PENDING, UNDER_REVIEW, RESOLVED
    }
}


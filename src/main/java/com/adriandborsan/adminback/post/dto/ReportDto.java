package com.adriandborsan.adminback.post.dto;

import com.adriandborsan.adminback.post.entities.PostEntity;
import com.adriandborsan.adminback.post.entities.Report;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class ReportDto {
    private Long id;
    private Report.ReportStatus status;
    private PostEntity post;
    private LocalDateTime createdDate;
}

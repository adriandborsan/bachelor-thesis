package com.adriandborsan.adminback.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogEntry {
    @Id
    private String id;
    private String createdAt;
    private String receivedAt = Instant.now().toString();
    private String message;

}

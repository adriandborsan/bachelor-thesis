package com.adriandborsan.clientback.log.dao;

import lombok.*;
import java.time.Instant;
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class LogEntry {
     private String createdAt = Instant.now().toString();
    @NonNull
    private String message;
}

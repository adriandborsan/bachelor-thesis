package com.adriandborsan.adminback.log.configurations;

import com.adriandborsan.adminback.log.documents.LogEntry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Sinks;

@Configuration
public class SinkConfiguration {
    @Bean
    public Sinks.Many<LogEntry> logEntrySink() {
        return Sinks.many().replay().limit(10, java.time.Duration.ofSeconds(10));
    }
}

package com.adriandborsan.adminback.handlers;

import com.adriandborsan.adminback.documents.LogEntry;
import com.adriandborsan.adminback.services.LogEntryService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Component
public class LogEntryHandler {

    private final LogEntryService logEntryService;
    private final Sinks.Many<LogEntry> logEntrySink;

    public LogEntryHandler(LogEntryService logEntryService, Sinks.Many<LogEntry> logEntrySink) {
        this.logEntryService = logEntryService;
        this.logEntrySink = logEntrySink;
    }


    public Mono<ServerResponse> getEntries(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(logEntrySink.asFlux(), LogEntry.class);
    }
}
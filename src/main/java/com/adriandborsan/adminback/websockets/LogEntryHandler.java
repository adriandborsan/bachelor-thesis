package com.adriandborsan.adminback.websockets;

import com.adriandborsan.adminback.documents.LogEntry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import reactor.core.publisher.Sinks;

import java.io.IOException;

@Component
public class LogEntryHandler extends TextWebSocketHandler {
    private final Sinks.Many<LogEntry> logEntrySink;
    private final ObjectMapper objectMapper;

    public LogEntryHandler(Sinks.Many<LogEntry> logEntrySink, ObjectMapper objectMapper) {
        this.logEntrySink = logEntrySink;
        this.objectMapper = objectMapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logEntrySink.asFlux()
                .map(this::convertToJson)
                .subscribe(
                logEntry  -> sendMessage(session, logEntry),
                error -> closeSessionOnError(session, error));
    }

    private void sendMessage(WebSocketSession session, String logEntry) {
        try {
            session.sendMessage(new TextMessage(logEntry));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String convertToJson(LogEntry logEntry) {
        try {
            return objectMapper.writeValueAsString(logEntry);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert log entry to JSON", e);
        }
    }

    private void closeSessionOnError(WebSocketSession session, Throwable error) {
        try {
            session.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
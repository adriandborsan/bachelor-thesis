package com.adriandborsan.adminback.websockets;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final LogEntryHandler logEntryHandler;
    private final AuthHandshakeInterceptor authHandshakeInterceptor;

    public WebSocketConfig(LogEntryHandler logEntryHandler, AuthHandshakeInterceptor authHandshakeInterceptor) {
        this.logEntryHandler = logEntryHandler;
        this.authHandshakeInterceptor = authHandshakeInterceptor;
    }

    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(logEntryHandler, "/api/v1/reactive/logs")
                .addInterceptors(authHandshakeInterceptor)
                .setAllowedOrigins("*");
    }
}

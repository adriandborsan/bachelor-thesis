package com.adriandborsan.adminback.configurations;

import com.adriandborsan.adminback.handlers.LogEntryHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@EnableWebFlux
public class ReactiveConfiguration {
    @Bean
    public RouterFunction<ServerResponse> routes(LogEntryHandler logEntryHandler) {
        return RouterFunctions.route(RequestPredicates.GET("/api/v1/reactive/logs"), logEntryHandler::getEntries);
    }
}

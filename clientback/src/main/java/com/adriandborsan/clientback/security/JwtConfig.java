package com.adriandborsan.clientback.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class JwtConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    String issuerUri;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/actuator/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
        return http.build();
    }
    @Bean
    public JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder) JwtDecoders.fromIssuerLocation(issuerUri);

        jwtDecoder.setJwtValidator(token -> OAuth2TokenValidatorResult.success());

        return jwtDecoder;
    }
}

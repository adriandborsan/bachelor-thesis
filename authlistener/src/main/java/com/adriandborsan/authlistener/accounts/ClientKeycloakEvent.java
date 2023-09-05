package com.adriandborsan.authlistener.accounts;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@JsonTypeName("com.github.aznamier.keycloak.event.provider.EventClientNotificationMqMsg")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientKeycloakEvent implements Serializable {

    private String id;
    private String realmId;
    private String clientId;
    private String userId;
    private String ipAddress;
    private Map<String, String> details;
    private String error;
    private String sessionId;
    private long time;
    private String type;

}
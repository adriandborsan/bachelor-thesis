package com.adriandborsan.authlistener;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@JsonTypeName("com.github.aznamier.keycloak.event.provider.EventAdminNotificationMqMsg")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminKeycloakEvent implements Serializable {

    private long time;
    private String realmId;
    private Map<String, String> authDetails;
    private String resourceType;
    private String operationType;
    private String representation;
    private String resourceTypeAsString;
}
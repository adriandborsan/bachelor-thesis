package com.adriandborsan.authlistener;

import com.adriandborsan.authlistener.user.UserEntity;
import com.adriandborsan.authlistener.user.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class KeycloakEventListener {

    private final UserService userService;

    public KeycloakEventListener(UserService userService) {
        this.userService = userService;
    }

    @RabbitListener(queues = "clientqueue", messageConverter = "jsonMessageConverter")
    public void handleClientEvent(@Payload ClientKeycloakEvent event) {

        if (event.getType().equals("REGISTER")) {
            userService.save(new UserEntity(event.getUserId(),event.getDetails().get("username")));
        }
    }

}
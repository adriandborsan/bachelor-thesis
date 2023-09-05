package com.adriandborsan.authlistener.accounts;

import com.adriandborsan.authlistener.accounts.neo4j.UserNode;
import com.adriandborsan.authlistener.accounts.neo4j.UserNodeRepository;
import com.adriandborsan.authlistener.accounts.user.UserEntity;
import com.adriandborsan.authlistener.accounts.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KeycloakEventListener {

    private final UserService userService;
    private final UserNodeRepository userNodeRepository;

    @RabbitListener(queues = "clientqueue", messageConverter = "jsonMessageConverter")
    public void handleClientEvent(@Payload ClientKeycloakEvent event) {

        if (event.getType().equals("REGISTER")) {
            userNodeRepository.save(new UserNode(event.getUserId()));
            userService.save(new UserEntity(event.getUserId(),event.getDetails().get("username")));
        }
    }

}
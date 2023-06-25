package com.adriandborsan.authlistener.user;

import com.adriandborsan.authlistener.neo4j.UserNode;
import com.adriandborsan.authlistener.neo4j.UserNodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserNodeRepository userNodeRepository;

    public <S extends UserEntity> S save(S entity) {
        userNodeRepository.save(new UserNode(entity.getId()));
        return userRepository.save(entity);
    }
}

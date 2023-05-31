package com.adriandborsan.authlistener.user;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public <S extends UserEntity> S save(S entity) {
        return userRepository.save(entity);
    }
}

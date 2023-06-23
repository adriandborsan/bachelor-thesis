package com.adriandborsan.clientback.post.controllers;

import com.adriandborsan.clientback.post.entities.UserEntity;
import com.adriandborsan.clientback.post.repositories.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin("*")
@RequiredArgsConstructor
public class UserController {
    private final UserEntityRepository userEntityRepository;

    @GetMapping("/{id}")
    public UserEntity findById(@PathVariable String id) {
        Logger.getAnonymousLogger().severe(id);
        return userEntityRepository.findById(id).get();
    }
}

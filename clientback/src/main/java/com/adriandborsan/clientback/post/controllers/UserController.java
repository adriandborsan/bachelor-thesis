package com.adriandborsan.clientback.post.controllers;

import com.adriandborsan.clientback.post.dto.UserUpdateDto;
import com.adriandborsan.clientback.post.entities.PostEntity;
import com.adriandborsan.clientback.post.entities.UserEntity;
import com.adriandborsan.clientback.post.services.PostService;
import com.adriandborsan.clientback.post.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin("*")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> findById(@PathVariable String id) {
        return userService.findUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable String id,
            @ModelAttribute UserUpdateDto userUpdateDto
    ) {
        try {
            userService.updateUser(id, userUpdateDto);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/posts")
    public ResponseEntity<Page<PostEntity>> getPostsById(@PathVariable String id, Pageable pageable) {
        return ResponseEntity.ok(userService.readAllPostsByUser(id, pageable));
    }
}

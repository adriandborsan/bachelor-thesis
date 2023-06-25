package com.adriandborsan.clientback.post.controllers;

import com.adriandborsan.clientback.post.dto.UserUpdateDto;
import com.adriandborsan.clientback.post.entities.PostEntity;
import com.adriandborsan.clientback.post.entities.UserEntity;
import com.adriandborsan.clientback.post.repositories.UserEntityRepository;
import com.adriandborsan.clientback.post.services.MinioService;
import com.adriandborsan.clientback.post.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin("*")
@RequiredArgsConstructor
public class UserController {
    private final UserEntityRepository userEntityRepository;
    private final PostService postService;
    private final MinioService minioService;

    @GetMapping("/{id}")
    public UserEntity findById(@PathVariable String id) {

        UserEntity userEntity = userEntityRepository.findById(id).get();
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+userEntity);
        return userEntity;
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable String id,
            @ModelAttribute UserUpdateDto userUpdateDto
    ) {
        String userId = postService.getCurrentUserId();

        if (!userId.equals(id)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        UserEntity userEntity = userEntityRepository.findById(id).get();

        if (userUpdateDto.getDisplayUsername() != null) {
            userEntity.setDisplayUsername(userUpdateDto.getDisplayUsername());
        }

        if (userUpdateDto.getFirstName() != null) {
            userEntity.setFirstName(userUpdateDto.getFirstName());
        }

        if (userUpdateDto.getLastName() != null) {
            userEntity.setLastName(userUpdateDto.getLastName());
        }

        if (userUpdateDto.getBio() != null) {
            userEntity.setBio(userUpdateDto.getBio());
        }

        if (userUpdateDto.getProfilePicture() != null) {
            if (userEntity.getProfilePicture() != null) {
                minioService.deleteProfilePicture(userEntity.getProfilePicture());
            }
            String originalFilename = userUpdateDto.getProfilePicture().getOriginalFilename();
            // save the new profile picture
            String uniqueFileName =  "/profile." +id +originalFilename.substring(originalFilename.lastIndexOf("."));
            minioService.saveProfilePicture(userUpdateDto.getProfilePicture(), uniqueFileName);

            // set the new profile picture file name
            userEntity.setProfilePicture(uniqueFileName);
        }

        // update the updatedAt timestamp
        userEntity.setUpdatedAt(LocalDateTime.now());

        // save the updated userEntity
        userEntityRepository.save(userEntity);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/posts")
    public Page<PostEntity> findById(@PathVariable String id, Pageable pageable) {
        Page<PostEntity> postEntities = postService.readAllPostsByUser(id, pageable);
        Logger.getAnonymousLogger().info(postEntities.toString());
        return postEntities;
    }
}

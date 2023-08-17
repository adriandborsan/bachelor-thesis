package com.adriandborsan.clientback.post.services;

import com.adriandborsan.clientback.post.dto.UserUpdateDto;
import com.adriandborsan.clientback.post.entities.PostEntity;
import com.adriandborsan.clientback.post.entities.UserEntity;
import com.adriandborsan.clientback.post.repositories.UserEntityRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserEntityRepository userEntityRepository;
    private final PostService postService;
    private final MinioService minioService;

    public Optional<UserEntity> findUserById(String id) {
        return userEntityRepository.findById(id);
    }

    public UserEntity updateUser(String id, UserUpdateDto userUpdateDto) {
        UserEntity userEntity = userEntityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        updateUserEntityFromDto(userEntity, userUpdateDto);
        userEntity.setUpdatedAt(LocalDateTime.now());
        return userEntityRepository.save(userEntity);
    }

    private void updateUserEntityFromDto(UserEntity userEntity, UserUpdateDto userUpdateDto) {
        Optional.ofNullable(userUpdateDto.getDisplayUsername()).ifPresent(userEntity::setDisplayUsername);
        Optional.ofNullable(userUpdateDto.getFirstName()).ifPresent(userEntity::setFirstName);
        Optional.ofNullable(userUpdateDto.getLastName()).ifPresent(userEntity::setLastName);
        Optional.ofNullable(userUpdateDto.getBio()).ifPresent(userEntity::setBio);
        if (userUpdateDto.getProfilePicture() != null) {
            updateProfilePicture(userEntity, userUpdateDto);
        }
    }

    private void updateProfilePicture(UserEntity userEntity, UserUpdateDto userUpdateDto) {
        Optional.ofNullable(userEntity.getProfilePicture()).ifPresent(minioService::deleteProfilePicture);
        String originalFilename = userUpdateDto.getProfilePicture().getOriginalFilename();
        String uniqueFileName = "/profile." + userEntity.getId() + originalFilename.substring(originalFilename.lastIndexOf("."));
        minioService.saveProfilePicture(userUpdateDto.getProfilePicture(), uniqueFileName);
        userEntity.setProfilePicture(uniqueFileName);
    }

    public Page<PostEntity> readAllPostsByUser(String id, Pageable pageable) {
        return postService.readAllPostsByUser(id, pageable);
    }
}


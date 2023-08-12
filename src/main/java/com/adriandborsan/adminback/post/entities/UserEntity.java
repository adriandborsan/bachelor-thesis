package com.adriandborsan.adminback.post.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class UserEntity {
    @Id
    private String id;
    private String displayUsername;
    private String firstName;
    private String lastName;
    private String bio;
    private String profilePicture;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

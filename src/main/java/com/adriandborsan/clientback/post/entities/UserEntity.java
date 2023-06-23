package com.adriandborsan.clientback.post.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="user_entity")
public class UserEntity {
    @Id
    private String id;
    private String displayUsername;
    private String firstName;
    private String lastName;
    private String bio;
    @OneToOne
    private FileEntity profilePicture;
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

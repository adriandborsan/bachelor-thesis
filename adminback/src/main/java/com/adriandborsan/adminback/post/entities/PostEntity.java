package com.adriandborsan.adminback.post.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class PostEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String message;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FileEntity> files;
    @ManyToOne
    private UserEntity userEntity;
    private LocalDateTime createdAt;
}

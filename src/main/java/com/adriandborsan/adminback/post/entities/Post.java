package com.adriandborsan.adminback.post.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class Post {
    @Id
    @GeneratedValue
    private Long id;
    @NonNull  private String title;
    @NonNull  private String message;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FileEntity> files;
}

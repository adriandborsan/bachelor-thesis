package com.adriandborsan.clientback.post.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

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
}

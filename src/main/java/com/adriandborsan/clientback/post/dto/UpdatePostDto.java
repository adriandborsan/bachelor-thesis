package com.adriandborsan.clientback.post.dto;

import com.adriandborsan.clientback.post.entities.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class UpdatePostDto {
    private String title;
    private String message;
    private List<Long> fileIdsToKeep=new ArrayList<>();
    private List<MultipartFile> newFiles=new ArrayList<>();

    public Post toEntity() {
        Post post = new Post();
        post.setTitle(this.title);
        post.setMessage(this.message);
        return post;
    }
}

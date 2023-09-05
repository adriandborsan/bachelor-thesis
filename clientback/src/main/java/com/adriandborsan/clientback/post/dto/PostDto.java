package com.adriandborsan.clientback.post.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class PostDto {
    @NonNull
    private String title;
    @NonNull  private String message;
    private List<MultipartFile> files=new ArrayList<>();

}
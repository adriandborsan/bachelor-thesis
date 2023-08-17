package com.adriandborsan.clientback.post.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserUpdateDto {
    private String displayUsername;
    private String firstName;
    private String lastName;
    private String bio;
    private MultipartFile profilePicture;
}

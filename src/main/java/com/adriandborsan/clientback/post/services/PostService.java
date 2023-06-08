package com.adriandborsan.clientback.post.services;

import com.adriandborsan.clientback.post.dto.PostDto;
import com.adriandborsan.clientback.post.dto.UpdatePostDto;
import com.adriandborsan.clientback.post.entities.FileEntity;
import com.adriandborsan.clientback.post.entities.Post;
import com.adriandborsan.clientback.post.repositories.PostRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final MinioService minioService;

    public PostService(PostRepository postRepository, MinioService minioService) {
        this.postRepository = postRepository;
        this.minioService = minioService;
    }

    public String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        Jwt jwt = jwtAuthenticationToken.getToken();
        return jwt.getClaims().get("sub").toString();
    }

    public Page<Post> findAll(int pageNumber, int pageSize, String sortBy, String order) {
        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        return postRepository.findAll(PageRequest.of(pageNumber, pageSize, sort));
    }

    public void create(PostDto postDto) {
        Post post = postDto.toEntity();
        post.setFiles(postDto.getFiles().stream().map(this::saveFile).toList());
        postRepository.save(post);
    }

    @NotNull
    private FileEntity saveFile(MultipartFile multipartFile) {
        String uniqueFileName ="/"+ getCurrentUserId() + "/" + UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
        minioService.save(multipartFile, uniqueFileName);
        FileEntity fileEntity = new FileEntity();
        fileEntity.setPath(uniqueFileName);
        fileEntity.setMimeType(multipartFile.getContentType());
        fileEntity.setOriginalName(multipartFile.getOriginalFilename());
        return fileEntity;
    }

    public void update(UpdatePostDto updatePostDto, Long id) {
        Post existingPost = postRepository.findById(id)
                .orElseGet(updatePostDto::toEntity);

        if (updatePostDto.getTitle() != null) {
            existingPost.setTitle(updatePostDto.getTitle());
        }
        if (updatePostDto.getMessage() != null) {
            existingPost.setMessage(updatePostDto.getMessage());
        }

        List<FileEntity> filesToDelete = existingPost.getFiles().stream()
                .filter(fileEntity -> !updatePostDto.getFileIdsToKeep().contains(fileEntity.getId()))
                .collect(Collectors.toList());

        filesToDelete.forEach(fileEntity -> minioService.delete(fileEntity.getPath()));
        existingPost.getFiles().removeAll(filesToDelete);

        updatePostDto.getNewFiles().forEach(multipartFile -> {
            FileEntity fileEntity = saveFile(multipartFile);
            existingPost.getFiles().add(fileEntity);
        });

        postRepository.save(existingPost);
    }

    public Post findById(Long id) {
        return postRepository.findById(id).get();
    }

    public void deleteById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        post.getFiles().forEach(fileEntity -> minioService.delete(fileEntity.getPath()));
        postRepository.deleteById(id);
    }

}

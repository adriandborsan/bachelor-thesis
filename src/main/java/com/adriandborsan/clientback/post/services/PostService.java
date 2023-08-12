package com.adriandborsan.clientback.post.services;

import com.adriandborsan.clientback.post.dto.PostDto;
import com.adriandborsan.clientback.post.dto.UpdatePostDto;
import com.adriandborsan.clientback.post.entities.FileEntity;
import com.adriandborsan.clientback.post.entities.PostEntity;
import com.adriandborsan.clientback.post.entities.Report;
import com.adriandborsan.clientback.post.entities.UserEntity;
import com.adriandborsan.clientback.post.nodes.PostNode;
import com.adriandborsan.clientback.post.nodes.UserNode;
import com.adriandborsan.clientback.post.nodes.repositories.PostNodeRepository;
import com.adriandborsan.clientback.post.nodes.repositories.UserNodeRepository;
import com.adriandborsan.clientback.post.repositories.PostEntityRepository;
import com.adriandborsan.clientback.post.repositories.ReportRepository;
import com.adriandborsan.clientback.post.repositories.UserEntityRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostEntityRepository postEntityRepository;
    private final MinioService minioService;
    private final ReportRepository reportRepository;
    private final UserEntityRepository userEntityRepository;
    private final UserNodeRepository userNodeRepository;
    private final PostNodeRepository postNodeRepository;

    public void report(Long postId) {
        Report report = new Report();
        report.setPostId(postId);
        reportRepository.save(report);
    }

    public String getCurrentUserId() {
        JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return (String) authentication.getToken().getClaims().get("sub");
    }

    public Page<PostEntity> findAll(Pageable pageable) {
        return postEntityRepository.findAll(pageable);
    }

    public PostEntity create(PostDto postDto) {
        String currentUserId = getCurrentUserId();
        UserEntity userEntity = userEntityRepository.findById(currentUserId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        UserNode userNode = userNodeRepository.findById(currentUserId).orElseThrow(() -> new EntityNotFoundException("UserNode not found"));

        PostEntity postEntity = new PostEntity();
        postEntity.setTitle(postDto.getTitle());
        postEntity.setMessage(postDto.getMessage());
        postEntity.setCreatedAt(LocalDateTime.now());
        postEntity.setUserEntity(userEntity);
        postEntity = postEntityRepository.save(postEntity);

        PostNode postNode = new PostNode(postEntity.getId());
        postNode.setAuthor(userNode);
        postNodeRepository.save(postNode);

        postEntity.setFiles(postDto.getFiles().stream().map(file -> saveFile(file, currentUserId)).collect(Collectors.toList()));
        return postEntityRepository.save(postEntity);
    }

    private FileEntity saveFile(MultipartFile multipartFile, String userId) {
        String uniqueFileName = "/" + userId + "/" + UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
        minioService.save(multipartFile, uniqueFileName);
        FileEntity fileEntity = new FileEntity();
        fileEntity.setPath(uniqueFileName);
        fileEntity.setMimeType(multipartFile.getContentType());
        fileEntity.setOriginalName(multipartFile.getOriginalFilename());
        return fileEntity;
    }

    public PostEntity update(UpdatePostDto updatePostDto, Long id) {
        PostEntity existingPostEntity = postEntityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post not found"));

        if (updatePostDto.getTitle() != null) {
            existingPostEntity.setTitle(updatePostDto.getTitle());
        }
        if (updatePostDto.getMessage() != null) {
            existingPostEntity.setMessage(updatePostDto.getMessage());
        }

        handleFileUpdates(existingPostEntity, updatePostDto);

        return postEntityRepository.save(existingPostEntity);
    }

    private void handleFileUpdates(PostEntity existingPostEntity, UpdatePostDto updatePostDto) {
        List<FileEntity> filesToDelete = existingPostEntity.getFiles().stream()
                .filter(fileEntity -> !updatePostDto.getFileIdsToKeep().contains(fileEntity.getId()))
                .collect(Collectors.toList());

        filesToDelete.forEach(fileEntity -> minioService.delete(fileEntity.getPath()));
        existingPostEntity.getFiles().removeAll(filesToDelete);

        String currentUserId = getCurrentUserId();
        updatePostDto.getNewFiles().forEach(multipartFile -> {
            FileEntity fileEntity = saveFile(multipartFile, currentUserId);
            existingPostEntity.getFiles().add(fileEntity);
        });
    }

    public PostEntity findById(Long id) {
        return postEntityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Post not found"));
    }

    public void deleteById(Long id) {
        PostEntity postEntity = findById(id);
        postEntity.getFiles().forEach(fileEntity -> minioService.delete(fileEntity.getPath()));
        postNodeRepository.deleteById(id);
        postEntityRepository.delete(postEntity);
    }

    public Page<PostEntity> readAllPostsByUser(String userId, Pageable pageable) {
        return postEntityRepository.findAllByUserEntityId(userId, pageable);
    }
}

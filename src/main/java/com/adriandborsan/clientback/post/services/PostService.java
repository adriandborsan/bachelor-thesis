package com.adriandborsan.clientback.post.services;

import com.adriandborsan.clientback.nodes.PostNode;
import com.adriandborsan.clientback.nodes.PostNodeRepository;
import com.adriandborsan.clientback.nodes.UserNode;
import com.adriandborsan.clientback.nodes.UserNodeRepository;
import com.adriandborsan.clientback.post.dto.PostDto;
import com.adriandborsan.clientback.post.dto.UpdatePostDto;
import com.adriandborsan.clientback.post.entities.FileEntity;
import com.adriandborsan.clientback.post.entities.PostEntity;
import com.adriandborsan.clientback.post.entities.Report;
import com.adriandborsan.clientback.post.entities.UserEntity;
import com.adriandborsan.clientback.post.repositories.PostEntityRepository;
import com.adriandborsan.clientback.post.repositories.ReportRepository;
import com.adriandborsan.clientback.post.repositories.UserEntityRepository;
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

    //@
    public Page<PostEntity> findAll(Pageable pageable) {
        return postEntityRepository.findAll(pageable);
    }

    //@
    public PostEntity create(PostDto postDto) {
        String currentUserId = getCurrentUserId();
        UserEntity userEntity = userEntityRepository.findById(currentUserId).get();
        UserNode userNode = userNodeRepository.findById(currentUserId).get();
        PostEntity postEntity = new PostEntity();
        postEntity.setTitle(postDto.getTitle());
        postEntity.setMessage(postDto.getMessage());
        postEntity.setCreatedAt(LocalDateTime.now());
        postEntity.setUserEntity(userEntity);
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaAAAAaaAA"+postEntity);
        postEntity = postEntityRepository.save(postEntity);
        System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb"+postEntity);
        PostNode postNode = new PostNode(postEntity.getId());
        postNode.setAuthor(userNode);
        postNode = postNodeRepository.save(postNode);
        postEntity.setFiles(postDto.getFiles().stream().map(this::saveFile).collect(Collectors.toList()));
        postEntity = postEntityRepository.save(postEntity);
        return postEntity;
    }

    //@
    private FileEntity saveFile(MultipartFile multipartFile) {
        String uniqueFileName = "/" + getCurrentUserId() + "/" + UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
        minioService.save(multipartFile, uniqueFileName);
        FileEntity fileEntity = new FileEntity();
        fileEntity.setPath(uniqueFileName);
        fileEntity.setMimeType(multipartFile.getContentType());
        fileEntity.setOriginalName(multipartFile.getOriginalFilename());
        return fileEntity;
    }

    //@
    public PostEntity update(UpdatePostDto updatePostDto, Long id) {
        PostEntity existingPostEntity = postEntityRepository.findById(id).get();

        if (updatePostDto.getTitle() != null) {
            existingPostEntity.setTitle(updatePostDto.getTitle());
        }
        if (updatePostDto.getMessage() != null) {
            existingPostEntity.setMessage(updatePostDto.getMessage());
        }

        List<FileEntity> filesToDelete = existingPostEntity.getFiles().stream()
                .filter(fileEntity -> !updatePostDto.getFileIdsToKeep().contains(fileEntity.getId()))
                .collect(Collectors.toList());

        filesToDelete.forEach(fileEntity -> minioService.delete(fileEntity.getPath()));
        existingPostEntity.getFiles().removeAll(filesToDelete);

        updatePostDto.getNewFiles().forEach(multipartFile -> {
            FileEntity fileEntity = saveFile(multipartFile);
            existingPostEntity.getFiles().add(fileEntity);
        });

       return postEntityRepository.save(existingPostEntity);
    }

    //@
    public PostEntity findById(Long id) {
        return postEntityRepository.findById(id).get();
    }

    //@
    public void deleteById(Long id) {
        PostEntity postEntity = postEntityRepository.findById(id).get();
        postEntity.getFiles().forEach(fileEntity -> minioService.delete(fileEntity.getPath()));
        postNodeRepository.delete(postNodeRepository.findById(id).get());
        postEntityRepository.delete(postEntity);
    }

    public Page<PostEntity> readAllPostsByUser(String userId, Pageable pageable) {
        return postEntityRepository.findAllByUserEntityId(userId, pageable);
    }

}

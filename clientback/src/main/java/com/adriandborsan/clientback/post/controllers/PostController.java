package com.adriandborsan.clientback.post.controllers;

import com.adriandborsan.clientback.post.dto.PostDto;
import com.adriandborsan.clientback.post.dto.UpdatePostDto;
import com.adriandborsan.clientback.post.entities.PostEntity;
import com.adriandborsan.clientback.post.services.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@CrossOrigin("*")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public Page<PostEntity> findAll(Pageable pageable) {
        return postService.findAll(pageable);
    }

    @PostMapping("/{id}/report")
    public void create(@PathVariable Long id) {
        postService.report(id);
    }

    @PostMapping
    public PostEntity create(@ModelAttribute PostDto postDto) {
      return  postService.create(postDto);
    }

    @GetMapping("/{id}")
    public PostEntity findById(@PathVariable Long id) {
        return postService.findById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (!postService.findById(id).getUserEntity().getId().equals(postService.getCurrentUserId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        postService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostEntity> update(@ModelAttribute UpdatePostDto newPost, @PathVariable Long id) {
        if (!postService.findById(id).getUserEntity().getId().equals(postService.getCurrentUserId())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
       return ResponseEntity.ok(postService.update(newPost, id));
    }
}

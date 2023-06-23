package com.adriandborsan.clientback.post.controllers;

import com.adriandborsan.clientback.post.dto.PostDto;
import com.adriandborsan.clientback.post.dto.UpdatePostDto;
import com.adriandborsan.clientback.post.entities.PostEntity;
import com.adriandborsan.clientback.post.services.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public void deleteById(@PathVariable Long id) {
        postService.deleteById(id);
    }

    @PutMapping("/{id}")
    public PostEntity update(@ModelAttribute UpdatePostDto newPost, @PathVariable Long id) {
       return postService.update(newPost, id);
    }
}

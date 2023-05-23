package com.adriandborsan.clientback.post.controllers;

import com.adriandborsan.clientback.post.entities.Post;
import com.adriandborsan.clientback.post.services.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@CrossOrigin("*")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<Post> findAll() {
        return postService.findAll();
    }

    @PostMapping
    public void create(@RequestBody Post post) {
        postService.create(post);
    }

    @GetMapping("/{id}")
    public Post findById(@PathVariable Long id) {
        return postService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        postService.deleteById(id);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Post newPost, @PathVariable Long id) {
        postService.update(newPost, id);
    }
}

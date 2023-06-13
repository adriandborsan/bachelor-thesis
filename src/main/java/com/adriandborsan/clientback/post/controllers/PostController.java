package com.adriandborsan.clientback.post.controllers;

import com.adriandborsan.clientback.post.dto.PostDto;
import com.adriandborsan.clientback.post.dto.UpdatePostDto;
import com.adriandborsan.clientback.post.entities.Post;
import com.adriandborsan.clientback.post.services.PostService;
import org.springframework.data.domain.Page;
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
    public Page<Post> findAll(@RequestParam(defaultValue = "0", required = false) int pageNumber,
                              @RequestParam(defaultValue = "100", required = false) int pageSize,
                              @RequestParam(defaultValue = "id", required = false) String sortBy,
                              @RequestParam(defaultValue = "asc", required = false) String order) {
        return postService.findAll(pageNumber, pageSize, sortBy, order);
    }

    @PostMapping("/{id}/report")
    public void create(@PathVariable Long id) {
        postService.report(id);
    }

    @PostMapping
    public void create(@ModelAttribute PostDto postDto) {
        postService.create(postDto);
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
    public void update(@ModelAttribute UpdatePostDto newPost, @PathVariable Long id) {
        postService.update(newPost, id);
    }
}

package com.adriandborsan.clientback.post.services;

import com.adriandborsan.clientback.post.entities.Post;
import com.adriandborsan.clientback.post.repositories.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public void create(Post post) {
        postRepository.save(post);
    }

    public Post findById(Long id) {
        return postRepository.findById(id).get();
    }

    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }

    public void update(Post newPost, Long id) {
        Post post = postRepository.findById(id).map(oldPost -> {
            oldPost.setTitle(newPost.getTitle());
            oldPost.setMessage(newPost.getMessage());
            return oldPost;
        }).orElseGet(() -> {
            newPost.setId(id);
            return newPost;
        });
        postRepository.save(post);
    }
}

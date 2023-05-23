package com.adriandborsan.clientback.post.repositories;

import com.adriandborsan.clientback.post.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}

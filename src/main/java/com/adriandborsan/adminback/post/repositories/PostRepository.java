package com.adriandborsan.adminback.post.repositories;

import com.adriandborsan.adminback.post.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}

package com.adriandborsan.adminback.post.repositories;

import com.adriandborsan.adminback.post.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
}

package com.adriandborsan.clientback.post.repositories;

import com.adriandborsan.clientback.post.entities.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostEntityRepository extends JpaRepository<PostEntity, Long> {
    Page<PostEntity> findAllByUserEntityId(String userEntityId, Pageable pageable);
}

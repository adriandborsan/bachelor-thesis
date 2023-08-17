package com.adriandborsan.clientback.post.repositories;

import com.adriandborsan.clientback.post.entities.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileEntityRepository extends JpaRepository<FileEntity,Long> {
}

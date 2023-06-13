package com.adriandborsan.adminback.post.repositories;

import com.adriandborsan.adminback.post.entities.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileEntityRepository extends JpaRepository<FileEntity,Long> {
}

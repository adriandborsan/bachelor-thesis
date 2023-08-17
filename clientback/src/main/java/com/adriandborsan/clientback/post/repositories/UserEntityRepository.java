package com.adriandborsan.clientback.post.repositories;

import com.adriandborsan.clientback.post.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEntityRepository extends JpaRepository<UserEntity,String> {
}

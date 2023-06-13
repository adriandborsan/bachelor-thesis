package com.adriandborsan.adminback.post.services;

import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MinioService {
    private final MinioClient minioClient;
    @Value("${minio.bucket.name}")
    private String bucket;
    public MinioService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public void delete(String fileName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucket)
                            .object(fileName)
                            .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

package com.adriandborsan.clientback.post.services;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MinioService {
    private final MinioClient minioClient;
    @Value("${minio.bucket.name}")
    private String bucket;

    private String profileBucket="profile-bucket";
    public MinioService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public void save(MultipartFile file, String uniqueFileName) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(uniqueFileName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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

    public void deleteProfilePicture(String profilePicture) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(profileBucket)
                            .object(profilePicture)
                            .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void saveProfilePicture(MultipartFile profilePicture, String uniqueFileName) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(profileBucket)
                            .object(uniqueFileName)
                            .stream(profilePicture.getInputStream(), profilePicture.getSize(), -1)
                            .contentType(profilePicture.getContentType())
                            .build());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

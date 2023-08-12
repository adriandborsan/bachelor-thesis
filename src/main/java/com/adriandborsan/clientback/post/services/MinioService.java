package com.adriandborsan.clientback.post.services;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MinioService {

    private final MinioClient minioClient;

    @Value("${minio.bucket.name}")
    private String defaultBucket;

    private static final String PROFILE_BUCKET = "profile-bucket";

    public void save(MultipartFile file, String uniqueFileName) {
        putObjectInBucket(file, uniqueFileName, defaultBucket);
    }

    public void delete(String fileName) {
        removeObjectFromBucket(fileName, defaultBucket);
    }

    public void deleteProfilePicture(String profilePicture) {
        removeObjectFromBucket(profilePicture, PROFILE_BUCKET);
    }

    public void saveProfilePicture(MultipartFile profilePicture, String uniqueFileName) {
        putObjectInBucket(profilePicture, uniqueFileName, PROFILE_BUCKET);
    }

    private void putObjectInBucket(MultipartFile file, String uniqueFileName, String bucket) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(uniqueFileName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build());
        } catch (Exception e) {
            throw new RuntimeException("Failed to save object in Minio", e);
        }
    }

    private void removeObjectFromBucket(String fileName, String bucket) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucket)
                            .object(fileName)
                            .build());
        } catch (Exception e) {
            throw new RuntimeException("Failed to remove object from Minio", e);
        }
    }
}

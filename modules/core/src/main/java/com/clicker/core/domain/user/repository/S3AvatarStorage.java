package com.clicker.core.domain.user.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.UUID;


@Service
@Slf4j
@RequiredArgsConstructor
public class S3AvatarStorage implements AvatarStorage {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String BUCKET;

    @Value("${aws.s3.base-url}")
    private String URL_TEMPLATE;

    @Value("${aws.s3.avatar.key-template}")
    private String KEY_TEMPLATE;

    @Value("${aws.s3.avatar.max-name-size}")
    private int MAX_NAME_SIZE;

    @Override
    public String save(UUID userId, MultipartFile avatar) {
        String avatarName = avatar.getName();
        if (avatarName.length() > MAX_NAME_SIZE) {
            log.error("Avatar have invalid length userId=%s, avatarName=%s".formatted(userId, avatarName));
            throw new RuntimeException("avatar name is to long, max is %s".formatted(MAX_NAME_SIZE));
        }
        try {
            String key = KEY_TEMPLATE.formatted(userId, avatarName);
            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(BUCKET)
                            .key(key)
                            .contentType(avatar.getContentType())
                            .acl(ObjectCannedACL.PUBLIC_READ)
                            .build(),
                    RequestBody.fromBytes(avatar.getBytes())
            );
            return URL_TEMPLATE.formatted(BUCKET, s3Client.serviceClientConfiguration().region().id()) + "/" + key;
        } catch(Exception e) {
            log.error("Error when saving avatar to s3", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String url) {
        String key = url.substring(URL_TEMPLATE.length());
        try {
            s3Client.deleteObject(
                    DeleteObjectRequest.builder()
                            .bucket(BUCKET)
                            .key(key)
                            .build()
            );
        } catch (Exception e) {
            log.error("Error when deleting avatar from s3", e);
            throw new RuntimeException(e);
        }
    }
}

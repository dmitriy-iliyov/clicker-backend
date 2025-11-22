package com.clicker.core.domain.user.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class S3AvatarStorage implements AvatarStorage {

    @Override
    public String save(UUID userId, MultipartFile image) {

        return "";
    }

    @Override
    public String get(String url) {
        return "";
    }

    @Override
    public void delete(String url) {

    }
}

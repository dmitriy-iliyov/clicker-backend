package com.clicker.core.domain.user.repository;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface AvatarStorage {
    String save(UUID userId, MultipartFile image);
    void delete(String url);
}

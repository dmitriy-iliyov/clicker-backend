package com.clicker.core.domain.user.models.dto;


public record PublicUserResponseDto(
        String username,
        String profilePictureUrl,
        String createdAt
) { }

package com.example.clicker.user.models.dto;


public record PublicUserResponseDto(
        String username,
        String profilePictureUrl,
        String createdAt
) { }

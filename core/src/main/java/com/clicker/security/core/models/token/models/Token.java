package com.example.clicker.security.core.models.token.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Token {

    private UUID id;
    private UUID subjectId;
    private List<String> authorities;
    private Instant issuedAt;
    private Instant expiresAt;

}
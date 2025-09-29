package com.clicker.core.messaging;


public record MessageDto (
        String recipient,
        String subject,
        String text
) {}

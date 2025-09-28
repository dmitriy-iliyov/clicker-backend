package com.clicker.messaging;


public record MessageDto (
        String recipient,
        String subject,
        String text
) {}

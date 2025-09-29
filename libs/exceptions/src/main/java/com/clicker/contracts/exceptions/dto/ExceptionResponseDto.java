package com.clicker.contracts.exceptions.dto;

public record ExceptionResponseDto(
        String code,
        String message,
        String description
) { }

package com.clicker.general.exceptions.dto;

public record ExceptionResponseDto(
        String code,
        String message,
        String description
) { }

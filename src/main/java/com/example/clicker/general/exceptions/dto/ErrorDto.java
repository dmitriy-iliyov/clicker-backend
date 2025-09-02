package com.example.clicker.general.exceptions.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ErrorDto(
        @JsonProperty("field")
        String field,
        @JsonProperty("message")
        String message
) { }
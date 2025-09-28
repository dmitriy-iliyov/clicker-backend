package com.example.clicker.currency.models.dto;

import com.example.clicker.currency.validation.update.CurrencyUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@CurrencyUpdate
public record CurrencyUpdateDto(
        @NotNull(message = "Currency id shouldn't be empty!")
        @Positive(message = "Currency id should be positive!")
        Long id,

        @NotBlank(message = "Currency code shouldn't be empty!")
        @Size(min = 1, max = 10, message = "Currency code should be greater than 1 and less then 10!")
        String code
) { }
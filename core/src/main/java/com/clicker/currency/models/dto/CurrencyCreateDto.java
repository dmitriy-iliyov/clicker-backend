package com.clicker.currency.models.dto;

import com.example.clicker.currency.validation.unique.UniqueCurrency;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CurrencyCreateDto(
        @NotBlank(message = "Currency code shouldn't be empty!")
        @Size(min = 1, max = 10, message = "Currency code should be greater than 1 and less then 10!")
        @UniqueCurrency
        String code
) { }

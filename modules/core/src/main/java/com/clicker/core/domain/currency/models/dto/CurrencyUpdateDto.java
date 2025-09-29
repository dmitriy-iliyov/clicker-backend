package com.clicker.core.domain.currency.models.dto;

import com.clicker.core.domain.currency.validation.update.CurrencyUpdate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@CurrencyUpdate
@Data
public class CurrencyUpdateDto {
        @JsonIgnore
        private Long id;

        @NotBlank(message = "Currency code shouldn't be empty!")
        @Size(min = 1, max = 10, message = "Currency code should be greater than 1 and less then 10!")
        private String code;
}
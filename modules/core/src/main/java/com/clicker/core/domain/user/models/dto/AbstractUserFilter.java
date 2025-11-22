package com.clicker.core.domain.user.models.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public abstract class AbstractUserFilter {

    @PositiveOrZero(message = "Page should be positive!")
    protected final int page;

    @Positive(message = "Size should be positive!")
    protected final int size;
}

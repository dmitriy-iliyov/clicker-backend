package com.clicker.core.domain.currency;

import com.clicker.core.domain.currency.models.CurrencyEntity;
import com.clicker.core.domain.currency.models.CurrencyType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public final class CurrencyDbInitializer {

    private final CurrencyRepository repository;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        repository.saveAll(
                Arrays.stream(CurrencyType.values())
                        .map(CurrencyEntity::new)
                        .toList()
        );
    }
}

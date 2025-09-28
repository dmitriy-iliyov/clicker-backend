package com.clicker.service;

import com.clicker.models.ClickerStateMapper;
import com.clicker.models.ClickerStateDto;
import com.clicker.models.TemporaryClickerStateEntity;
import com.clicker.repository.InMemoryClickerStateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClickerStateServiceImpl implements ClickerStateService {

    private final InMemoryClickerStateRepository inMemoryRepository;
    private final PermanentClickerStateService permanentService;
    private final ClickerStateMapper mapper;

    @Override
    public void save(UUID userId) {
        inMemoryRepository.findById(userId)
                .ifPresent(tmpEntity -> {
                    permanentService.save(mapper.toDto(tmpEntity));
                    inMemoryRepository.deleteById(userId);
                });
    }

    @Override
    public void update(UUID userId, Float probability) {
        if (!inMemoryRepository.updateById(userId, probability)) {
            throw new ClickProcessException();
        }
    }

    @Override
    public ClickerStateDto findByUserId(UUID userId) {
        return permanentService.findByUserId(userId);
    }

    @Override
    public Float findProbabilityByUserId(UUID userId) {
        TemporaryClickerStateEntity tmpState = inMemoryRepository.findById(userId).orElse(null);
        if (tmpState == null) {
            return permanentService.findByUserId(userId).probability();
        }
        return tmpState.probability();
    }
}


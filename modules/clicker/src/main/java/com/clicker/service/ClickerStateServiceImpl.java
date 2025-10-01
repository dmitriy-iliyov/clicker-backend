package com.clicker.service;

import com.clicker.exception.ClickProcessException;
import com.clicker.models.ClickerStateDto;
import com.clicker.models.ClickerStateMapper;
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
        inMemoryRepository.findById(userId.toString())
                .ifPresent(tmpEntity -> {
                    permanentService.save(mapper.toDto(tmpEntity));
                    inMemoryRepository.deleteById(userId.toString());
                });
    }

    @Override
    public void update(UUID userId, float probability, int clickCount) {
        if (!inMemoryRepository.updateById(userId, probability, clickCount)) {
            throw new ClickProcessException();
        }
    }

    @Override
    public ClickerStateDto findByUserId(UUID userId) {
        TemporaryClickerStateEntity tmpState = inMemoryRepository.findById(userId.toString()).orElse(null);
        if (tmpState == null) {
            ClickerStateDto dto = permanentService.findByUserId(userId);
            tmpState = inMemoryRepository.save(mapper.toTmpEntity(dto));
        }
        return mapper.toDto(tmpState);
    }
}


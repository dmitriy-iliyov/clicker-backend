package com.clicker.service;

import com.clicker.models.ClickerStateMapper;
import com.clicker.models.ClickerStateDto;
import com.clicker.models.ClickerStateEntity;
import com.clicker.repository.PermanentClickerStateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PermanentClickerStateServiceImpl implements PermanentClickerStateService {

    private final PermanentClickerStateRepository repository;
    private final ClickerStateMapper mapper;

    @Transactional
    @Override
    public void save(ClickerStateDto dto) {
        ClickerStateEntity entity = repository.findById(dto.userId()).orElse(null);
        if (entity != null) {
            mapper.updateEntityFromDto(dto, entity);
        } else {
            entity = mapper.toEntity(dto);
        }
        repository.save(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public ClickerStateDto findByUserId(UUID userId) {
        ClickerStateEntity entity = repository.findById(userId).orElse(null);
        if (entity == null) {
            entity = repository.save(new ClickerStateEntity(userId));
        }
        return mapper.toDto(entity);
    }
}

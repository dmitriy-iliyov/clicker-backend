package com.clicker.service;

import com.clicker.models.ClickerStateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClickerServiceImpl implements ClickerService {

    private static final float PROBABILITY = 100.0f;
    private static final float DECREASE_STEP = 0.1f;
    private final ClickerStateService stateService;

    @Override
    public ClickerStateDto loadClickerData(UUID userId) {
        return stateService.findByUserId(userId);
    }
    
    @Override
    public ClickerStateDto click(UUID userId) {
        float factor = (float) Math.pow(10, 1);
        float currentRandomProbability = Math.round(new SecureRandom().nextFloat() * 100 * factor) / factor;
        ClickerStateDto dto = stateService.findByUserId(userId);
        System.out.println(dto);
        Float lastProbability = dto.probability();
        int clickCount = dto.clicksCount();
        if (lastProbability == null) {
            lastProbability = PROBABILITY;
        }
        if(currentRandomProbability < lastProbability) {
            lastProbability = lastProbability - DECREASE_STEP;
            clickCount += 1;
        } else {
            lastProbability = PROBABILITY;
            clickCount = 0;
        }
        lastProbability = Math.round(lastProbability * factor) / factor;
        stateService.update(userId, lastProbability, clickCount);
        return new ClickerStateDto(userId, lastProbability, clickCount);
    }

    @Override
    public void saveClickerData(UUID userId) {
        stateService.save(userId);
    }
}

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
    private final ClickerStateService clickerStateService;

    @Override
    public ClickerStateDto loadClickerData(UUID userId) {
        return clickerStateService.findByUserId(userId);
    }
    
    @Override
    public float click(UUID userId) {
        float factor = (float) Math.pow(10, 1);
        float currentRandomProbability = Math.round(new SecureRandom().nextFloat() * 100 * factor) / factor;
        Float lastUserProbability = clickerStateService.findProbabilityByUserId(userId);
        if (lastUserProbability == null) {
            lastUserProbability = PROBABILITY;
        }
        if(currentRandomProbability < lastUserProbability) {
            lastUserProbability = lastUserProbability - DECREASE_STEP;
        } else {
            lastUserProbability = PROBABILITY;
        }
        lastUserProbability = Math.round(lastUserProbability * factor) / factor;
        clickerStateService.update(userId, lastUserProbability);
        return lastUserProbability;
    }

    @Override
    public void saveClickerData(UUID userId) {
        clickerStateService.save(userId);
    }
}

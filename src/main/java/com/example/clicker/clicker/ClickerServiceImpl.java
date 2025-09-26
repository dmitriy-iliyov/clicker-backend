package com.example.clicker.clicker;

import com.example.clicker.clicker.repository.DefaultProbabilityRepository;
import com.example.clicker.clicker.repository.InMemoryProbabilityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Log4j2
@RequiredArgsConstructor
public class ClickerServiceImpl implements ClickerService {

    private static final float PROBABILITY = 100.0f;
    private static final float DECREASE_STEP = 0.1f;
    private final Map<UUID, Float> probabilities = new ConcurrentHashMap<>();
    private final InMemoryProbabilityRepository inMemoryProbabilityRepository;
    private final DefaultProbabilityRepository defaultProbabilityRepository;

    @Override
    public float countProbability(UUID userId) {
        float factor = (float) Math.pow(10, 1);
        float currentRandomProbability = Math.round(new SecureRandom().nextFloat() * 100 * factor) / factor;
        float lastUserProbability = probabilities.computeIfAbsent(userId, k -> PROBABILITY);
        if(currentRandomProbability < lastUserProbability) {
            lastUserProbability = lastUserProbability - DECREASE_STEP;
        } else {
            lastUserProbability = PROBABILITY;
        }
        lastUserProbability = Math.round(lastUserProbability * factor) / factor;
        probabilities.put(userId, lastUserProbability);
        return lastUserProbability;
    }

//    @Override
//    public float countProbability(UUID userId) {
//        float factor = (float) Math.pow(10, 1);
//        log.info("factor={}", factor);
//        float currentRandomProbability = Math.round(new SecureRandom().nextFloat() * 100 * factor) / factor;
//        log.info("currentRandomProbability={}", currentRandomProbability);
//        float lastUserProbability = probabilities.computeIfAbsent(userId, k -> PROBABILITY);
//        log.info("lastUserProbability={}", lastUserProbability);
//        if(currentRandomProbability < lastUserProbability) {
//            lastUserProbability = lastUserProbability - DECREASE_STEP;
//        } else {
//            lastUserProbability = PROBABILITY;
//        }
//        log.info("lastUserProbability={}", lastUserProbability);
//        lastUserProbability = Math.round(lastUserProbability * factor) / factor;
//        probabilities.put(userId, lastUserProbability);
//        log.info("probabilities={}", probabilities);
//        return lastUserProbability;
//    }
}

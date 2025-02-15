package com.htps.service;

import com.htps.dto.HealthStatsDTO;
import com.htps.entities.HealthStats;
import com.htps.repository.HealthStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class HealthStatsServiceImpl implements HealthStatsService {

    @Autowired
    private HealthStatsRepository healthStatsRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public HealthStatsDTO getStatsByUserId(Long userId) {
        HealthStats stats = healthStatsRepository.findByUserId(userId);
        if (stats == null) {
            return new HealthStatsDTO(userId, 0.0, 0.0, 0.0);
        }
        return new HealthStatsDTO(stats.getUserId(), stats.getWeight(), stats.getCaloriesBurned(), stats.getCaloriesConsumed());
    }

    @Override
    public HealthStatsDTO updateHealthStats(Long userId, HealthStatsDTO healthStatsDTO) {
        HealthStats stats = healthStatsRepository.findByUserId(userId);

        if (stats == null) {
            stats = new HealthStats();
            stats.setUserId(userId);
        }

        stats.setWeight(healthStatsDTO.getWeight());
        stats.setCaloriesBurned(healthStatsDTO.getCaloriesBurned());
        stats.setCaloriesConsumed(healthStatsDTO.getCaloriesConsumed());

        healthStatsRepository.save(stats);

        HealthStatsDTO updatedStatsDTO = new HealthStatsDTO(stats.getUserId(), stats.getWeight(), stats.getCaloriesBurned(), stats.getCaloriesConsumed());

        messagingTemplate.convertAndSend("/topic/stats", updatedStatsDTO); 

        return updatedStatsDTO;
    }
}

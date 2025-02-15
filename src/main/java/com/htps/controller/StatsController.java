package com.htps.controller;

import com.htps.dto.HealthStatsDTO;
import com.htps.service.HealthStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stats")
public class StatsController {

    @Autowired
    private HealthStatsService healthStatsService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @GetMapping("/{userId}")
    public HealthStatsDTO getStats(@PathVariable Long userId) {
        return healthStatsService.getStatsByUserId(userId);
    }

    @PostMapping("/{userId}")
    public HealthStatsDTO updateHealthStats(@PathVariable Long userId, @RequestBody HealthStatsDTO healthStatsDTO) {
        HealthStatsDTO updatedStats = healthStatsService.updateHealthStats(userId, healthStatsDTO);
        messagingTemplate.convertAndSend("/topic/stats", updatedStats); // WebSocket update
        return updatedStats;
    }
}


package com.htps.service;

import com.htps.dto.HealthStatsDTO;

public interface HealthStatsService {
    HealthStatsDTO getStatsByUserId(Long userId);
    HealthStatsDTO updateHealthStats(Long userId, HealthStatsDTO healthStatsDTO); // Updated to return DTO
}


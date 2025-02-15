package com.htps.service;

import com.htps.dto.ProgressDTO;

public interface ProgressService {
    ProgressDTO getProgressByUserId(Long userId);
    ProgressDTO updateProgress(Long userId, ProgressDTO progressDTO);
}

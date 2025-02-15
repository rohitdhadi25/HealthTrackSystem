package com.htps.controller;

import com.htps.dto.ProgressDTO;
import com.htps.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/progress")
public class ProgressController {

    private static final Logger logger = LoggerFactory.getLogger(ProgressController.class);

    @Autowired
    private ProgressService progressService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getProgress(@PathVariable Long userId) {
        logger.info("Fetching progress for user ID: {}", userId);
        try {
            ProgressDTO progressDTO = progressService.getProgressByUserId(userId);
            return ResponseEntity.ok(progressDTO);
        } catch (Exception e) {
            logger.error("Error fetching progress: {}", e.getMessage());
            return ResponseEntity.internalServerError().body("Error fetching progress");
        }
    }

    @PostMapping("/{userId}")
    public ResponseEntity<?> updateProgress(@PathVariable Long userId, @RequestBody ProgressDTO progressDTO) {
        logger.info("Updating progress for user ID: {}", userId);
        try {
            ProgressDTO updatedProgress = progressService.updateProgress(userId, progressDTO);
            messagingTemplate.convertAndSend("/topic/progress", updatedProgress); // WebSocket update
            return ResponseEntity.ok(updatedProgress);
        } catch (Exception e) {
            logger.error("Error updating progress: {}", e.getMessage());
            return ResponseEntity.internalServerError().body("Error updating progress");
        }
    }
}

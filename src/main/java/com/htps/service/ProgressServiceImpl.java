
package com.htps.service;

import com.htps.dto.ProgressDTO;
import com.htps.entities.Progress;
import com.htps.repository.ProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProgressServiceImpl implements ProgressService {

    @Autowired
    private ProgressRepository progressRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public ProgressDTO getProgressByUserId(Long userId) {
        List<Progress> progressList = progressRepository.findByUserId(userId);
        if (progressList.isEmpty()) {
            return new ProgressDTO(userId, 0.0, 0.0, "No Data");
        }

   
        Progress progress = progressList.get(progressList.size() - 1);
        return new ProgressDTO(progress.getUserId(), progress.getWeight(), progress.getBodyFatPercentage(), progress.getStatus());
    }

    @Override
    public ProgressDTO updateProgress(Long userId, ProgressDTO progressDTO) {
        Progress progress = new Progress();
        progress.setUserId(userId);
        progress.setWeight(progressDTO.getWeight());
        progress.setBodyFatPercentage(progressDTO.getBodyFatPercentage());
        progress.setStatus(progressDTO.getStatus());

        progress = progressRepository.save(progress); 

        ProgressDTO updatedProgressDTO = new ProgressDTO(progress.getUserId(), progress.getWeight(), progress.getBodyFatPercentage(), progress.getStatus());

        messagingTemplate.convertAndSend("/topic/progress", updatedProgressDTO); 

        return updatedProgressDTO;
    }
}


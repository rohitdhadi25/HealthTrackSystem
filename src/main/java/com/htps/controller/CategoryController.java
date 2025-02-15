package com.htps.controller;

import com.htps.dto.CategoryDTO;
import com.htps.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*") // Allow frontend access
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @GetMapping
    public List<CategoryDTO> getCategories() {
        return categoryService.getCategories();
    }

    @PostMapping
    public CategoryDTO addCategory(@RequestBody CategoryDTO categoryDTO) {
        CategoryDTO savedCategory = categoryService.addCategory(categoryDTO);
        messagingTemplate.convertAndSend("/topic/categories", savedCategory); // WebSocket update
        return savedCategory;
    }
}

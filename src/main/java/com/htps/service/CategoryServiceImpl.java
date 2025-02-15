package com.htps.service;

import com.htps.dto.CategoryDTO;
import com.htps.entities.Category;
import com.htps.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public List<CategoryDTO> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> new CategoryDTO(category.getName(), category.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO addCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setValue(categoryDTO.getValue());

        category = categoryRepository.save(category);
        CategoryDTO savedCategoryDTO = new CategoryDTO(category.getName(), category.getValue());

        messagingTemplate.convertAndSend("/topic/categories", savedCategoryDTO); // WebSocket update

        return savedCategoryDTO;
    }
}

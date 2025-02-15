package com.htps.service;

import com.htps.dto.CategoryDTO;
import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getCategories();
    CategoryDTO addCategory(CategoryDTO categoryDTO); // Added method
}

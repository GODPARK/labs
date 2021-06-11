package com.board.springbd.service;

import com.board.springbd.exception.CategoryException;
import com.board.springbd.model.Category;
import com.board.springbd.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> searchCategoryAll() {
        return categoryRepository.findByState(true);
    }

    public Category searchCategoryById(long categoryId) {
        Optional<Category> category = categoryRepository.findByCategoryIdAndState(categoryId, true);
        if (category.isEmpty()) throw new CategoryException("category is empty");
        return category.get();
    }

    public Category saveCategory(Category category) {
        if (category.getCategoryName().isBlank()) throw new CategoryException("category name is blank");
        Category newCategory = Category.builder()
                .categoryName(category.getCategoryName())
                .state(true).build();
        return categoryRepository.save(newCategory);
    }
}

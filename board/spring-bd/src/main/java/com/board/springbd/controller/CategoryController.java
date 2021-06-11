package com.board.springbd.controller;

import com.board.springbd.model.Category;
import com.board.springbd.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping(path = "/{categoryId}", produces = "application/json")
    public ResponseEntity<Category> getCategoryById(@PathVariable(value = "categoryId") long categoryId) {
        return ResponseEntity.ok().body(categoryService.searchCategoryById(categoryId));
    }

    @GetMapping(path = "/all", produces = "application/json")
    public ResponseEntity<List<Category>> getAllCategory() {
        return ResponseEntity.ok().body(categoryService.searchCategoryAll());
    }

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Category> saveCategory(@RequestBody Category category) {
        return ResponseEntity.ok().body(categoryService.saveCategory(category));
    }
}

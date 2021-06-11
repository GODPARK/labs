package com.board.springbd.repository;

import com.board.springbd.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByCategoryIdAndState(Long categoryId, boolean state);
    List<Category> findByState(boolean state);
}

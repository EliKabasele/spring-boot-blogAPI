package com.congonumerictech.springbootblogrestapi.categories.service;

import com.congonumerictech.springbootblogrestapi.categories.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto getCategoryById( Long categoryId);

    List<CategoryDto> getAllCategories();

    CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto);

    String deleteCategory(Long categoryId);
}

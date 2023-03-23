package com.congonumerictech.springbootblogrestapi.categories.service;

import com.congonumerictech.springbootblogrestapi.categories.dto.CategoryDto;
import com.congonumerictech.springbootblogrestapi.categories.entity.Category;
import com.congonumerictech.springbootblogrestapi.categories.repository.CategoryRepository;
import com.congonumerictech.springbootblogrestapi.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService{

    private CategoryRepository categoryRepository;
    private ModelMapper mapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = mapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return mapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategoryById(Long categoryId) {
        Category foundCategory = findCategoryById(categoryId);
        return mapper.map(foundCategory, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category ->
                        mapper.map(category, CategoryDto.class)).collect(Collectors.toList()
                );
    }

    @Override
    public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {
        Category categoryToUpdate = findCategoryById(categoryId);
        categoryToUpdate.setName(categoryDto.getName());
        categoryToUpdate.setDescription(categoryDto.getDescription());

        Category savedCategory = categoryRepository.save(categoryToUpdate);

        return mapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = findCategoryById(categoryId);
        categoryRepository.delete(category);
        return "Category deleted successfully!";
    }

    private Category findCategoryById( Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "id", categoryId));
    }


}

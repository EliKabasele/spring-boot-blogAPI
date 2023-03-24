package com.congonumerictech.springbootblogrestapi.categories.repository;

import com.congonumerictech.springbootblogrestapi.categories.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}

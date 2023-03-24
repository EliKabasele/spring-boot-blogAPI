package com.congonumerictech.springbootblogrestapi.posts.repository;

import com.congonumerictech.springbootblogrestapi.posts.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByCategoryId(Long categoryId);
}

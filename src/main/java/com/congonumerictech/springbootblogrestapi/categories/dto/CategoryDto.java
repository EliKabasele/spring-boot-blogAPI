package com.congonumerictech.springbootblogrestapi.categories.dto;

import com.congonumerictech.springbootblogrestapi.posts.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Long id;
    private String name;
    private String description;
    private Set<Post> posts;
}


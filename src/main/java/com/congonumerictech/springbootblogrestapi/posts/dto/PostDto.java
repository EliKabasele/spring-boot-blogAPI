package com.congonumerictech.springbootblogrestapi.posts.dto;

import com.congonumerictech.springbootblogrestapi.comments.dto.CommentDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class PostDto {

    private Long id;

    @NotNull
    @Size(min = 2, message = "a title must have at least 2 characters")
    private String title;

    @NotNull
    @Size(min = 5, message = "a description must have at least 5 characters")
    private String description;

    @NotNull
    @Size(min = 5, message = "a content must have at least 5 characters")
    private String content;

    private Set<CommentDto> comments;
}

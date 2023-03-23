package com.congonumerictech.springbootblogrestapi.comments.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {

    private Long id;

    @NotNull(message = "Name should not be empty")
    @Size(min = 2, message = "Name must have at least 2 characters")
    private String name;

    @NotNull(message = "Email should not be empty")
    @Email
    private String email;

    @NotNull(message = "Body should not be empty")
    @Size(min = 5, message = "Name must have at least 2 characters")
    private String body;
}

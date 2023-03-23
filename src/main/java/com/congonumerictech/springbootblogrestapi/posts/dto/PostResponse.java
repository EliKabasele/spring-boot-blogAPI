package com.congonumerictech.springbootblogrestapi.posts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private List<PostDto> pageContent;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private Boolean last;
}

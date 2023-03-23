package com.congonumerictech.springbootblogrestapi.posts.service;

import com.congonumerictech.springbootblogrestapi.posts.dto.PostDto;
import com.congonumerictech.springbootblogrestapi.posts.dto.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    List<PostDto> getAllPosts();

    PostResponse getPostsByPage(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(Long id);

    PostDto updatePost(Long id, PostDto postDto);

    void deletePost(Long id);
}

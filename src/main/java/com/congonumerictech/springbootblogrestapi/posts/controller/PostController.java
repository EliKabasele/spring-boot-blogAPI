package com.congonumerictech.springbootblogrestapi.posts.controller;

import com.congonumerictech.springbootblogrestapi.posts.dto.PostDto;
import com.congonumerictech.springbootblogrestapi.posts.dto.PostResponse;
import com.congonumerictech.springbootblogrestapi.posts.service.PostService;
import com.congonumerictech.springbootblogrestapi.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPosts(@Valid @RequestBody PostDto postDto) {
        PostDto createdPost = postService.createPost(postDto);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> allPosts = postService.getAllPosts();
        return ResponseEntity.ok(allPosts);
    }

    @GetMapping("/pagination")
    public ResponseEntity<PostResponse> getPostsByPage(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        PostResponse postResponse = postService.getPostsByPage(pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") Long postId) {
        PostDto postById = postService.getPostById(postId);
        return ResponseEntity.ok(postById);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable("id") Long postId, @Valid @RequestBody PostDto postDto) {
        PostDto updatedPost = postService.updatePost(postId, postDto);
        return ResponseEntity.ok(updatedPost);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok("Post successfully deleted!");
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable("id") Long categoryId) {
        return ResponseEntity.ok(postService.getPostsByCategory(categoryId));
    }
}

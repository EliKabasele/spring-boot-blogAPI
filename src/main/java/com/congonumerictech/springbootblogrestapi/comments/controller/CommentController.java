package com.congonumerictech.springbootblogrestapi.comments.controller;

import com.congonumerictech.springbootblogrestapi.comments.dto.CommentDto;
import com.congonumerictech.springbootblogrestapi.comments.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/posts")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{post-id}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable("post-id") Long postId,
                                                    @Valid @RequestBody CommentDto commentDto) {
        CommentDto newComment = commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(newComment, HttpStatus.CREATED);
    }

    @GetMapping("/{post-id}/comments")
    public ResponseEntity<List<CommentDto>> getAllCommentsById(@PathVariable("post-id") Long postId) {
        List<CommentDto> commentsByPostId = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(commentsByPostId);
    }

    @GetMapping("/{post-id}/comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("post-id") Long postId,
                                                     @PathVariable("id") Long commentId) {
        CommentDto foundComment = commentService.getCommentById(postId, commentId);
        return ResponseEntity.ok(foundComment);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{post-id}/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable("post-id") Long postId,
                                                          @PathVariable("id") Long commentId,
                                                          @Valid @RequestBody CommentDto commentDto) {
        CommentDto updatedComment = commentService.updateComment(postId, commentId, commentDto);
        return ResponseEntity.ok(updatedComment);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{post-id}/comments/{id}")
    ResponseEntity<String> deleteCommentById(@PathVariable("post-id") Long postId,
                                             @PathVariable("id") Long commentId) {
        commentService.deleteCommentById(postId, commentId);
        return ResponseEntity.ok("Comment Deleted Successfully");
    }


}

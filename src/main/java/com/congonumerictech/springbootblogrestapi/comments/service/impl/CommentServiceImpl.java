package com.congonumerictech.springbootblogrestapi.comments.service.impl;

import com.congonumerictech.springbootblogrestapi.comments.dto.CommentDto;
import com.congonumerictech.springbootblogrestapi.comments.entity.Comment;
import com.congonumerictech.springbootblogrestapi.comments.repository.CommentRepository;
import com.congonumerictech.springbootblogrestapi.comments.service.CommentService;
import com.congonumerictech.springbootblogrestapi.exception.BlogAPIException;
import com.congonumerictech.springbootblogrestapi.exception.ResourceNotFoundException;
import com.congonumerictech.springbootblogrestapi.posts.entity.Post;
import com.congonumerictech.springbootblogrestapi.posts.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper mapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        // get Post with given id
        Post post = findPostById(postId);

        // transform to Comment-Entity to be saved in DB
        Comment comment = mapToEntity(commentDto);
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);

        // transform to DTO to be returned
        return mapToDTO(savedComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
        Post post = findPostById(postId);
        List<Comment> comments = commentRepository.findByPostId(post.getId());
        return comments.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        Post post = findPostById(postId);
        Comment comment = findCommentById(commentId);

        checkIfCommentBelongsToPost(post, comment);
        return mapToDTO(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) {
        Post post = findPostById(postId);
        Comment comment = findCommentById(commentId);

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        checkIfCommentBelongsToPost(post, comment);
        Comment updatedComment = commentRepository.save(comment);
        return mapToDTO(updatedComment);
    }

    @Override
    public void deleteCommentById(Long postId, Long commentId) {
        Post post = findPostById(postId);
        Comment comment = findCommentById(commentId);
        checkIfCommentBelongsToPost(post, comment);

        commentRepository.deleteById(commentId);
    }

    private void checkIfCommentBelongsToPost(Post post, Comment comment) {
        if(!(comment.getPost().getId().equals(post.getId()))) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment doesn't belong to Post");
        }
    }

    private Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", commentId)
        );
    }
    private Post findPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId)
        );
    }

    private CommentDto mapToDTO(Comment comment) {
        return mapper.map(comment, CommentDto.class);
    }
    private Comment mapToEntity(CommentDto commentDto) {
        return mapper.map(commentDto, Comment.class);
    }
}

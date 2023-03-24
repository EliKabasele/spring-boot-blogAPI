package com.congonumerictech.springbootblogrestapi.posts.service.impl;

import com.congonumerictech.springbootblogrestapi.categories.entity.Category;
import com.congonumerictech.springbootblogrestapi.categories.repository.CategoryRepository;
import com.congonumerictech.springbootblogrestapi.exception.ResourceNotFoundException;
import com.congonumerictech.springbootblogrestapi.posts.dto.PostDto;
import com.congonumerictech.springbootblogrestapi.posts.dto.PostResponse;
import com.congonumerictech.springbootblogrestapi.posts.entity.Post;
import com.congonumerictech.springbootblogrestapi.posts.repository.PostRepository;
import com.congonumerictech.springbootblogrestapi.posts.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;
    private CategoryRepository categoryRepository;
    private ModelMapper mapper;

    public PostServiceImpl(PostRepository postRepository,
                           ModelMapper mapper,
                           CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.mapper = mapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        Category category = findCategoryById(postDto.getCategoryId());

        // Transform received DTO --> Entity/Model-class to be saved in DB
        Post post = mapToEntity(postDto);
        post.setCategory(category);

        // Use Repository to save the Entity in the DB
        Post newPost = postRepository.save(post);

        // Transform Entity/Model-class --> DTO, to be returned
        return mapToDTO(newPost);
    }

    @Override
    public List<PostDto> getAllPosts() {

        List<Post> postList = postRepository.findAll();
        return postList.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getPostsByCategory(Long categoryId) {
       if(!(categoryRepository.existsById(categoryId))) {
           throw new ResourceNotFoundException("Category", "id", categoryId);
       }
        List<Post> postsByCategory = postRepository.findByCategoryId(categoryId);
        return postsByCategory.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PostResponse getPostsByPage(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> postPage = postRepository.findAll(pageable);
        List<Post> content = postPage.getContent();
        List<PostDto> contentDto = content.stream().map(this::mapToDTO).collect(Collectors.toList());

        return constructPostResponse(postPage, contentDto);
    }

    @Override
    public PostDto getPostById(Long id) {
        Post foundPost = findPostById(id);
        return mapToDTO(foundPost);
    }

    @Override
    public PostDto updatePost(Long id, PostDto postDto) {
        Category category = findCategoryById(postDto.getCategoryId());

        Post postToUpdate = findPostById(id);

        postToUpdate.setTitle(postDto.getTitle());
        postToUpdate.setDescription(postDto.getDescription());
        postToUpdate.setContent(postDto.getContent());
        postToUpdate.setCategory(category);

        Post updatedPost = postRepository.save(postToUpdate);
        return mapToDTO(updatedPost);
    }

    @Override
    public void deletePost(Long id) {
        Post postToDelete = findPostById(id);
        postRepository.delete(postToDelete);
    }

    private PostResponse constructPostResponse(Page<Post> postPage, List<PostDto> contentDto) {
        PostResponse postResponse = new PostResponse();
        postResponse.setPageContent(contentDto);
        postResponse.setPageNo(postPage.getNumber());
        postResponse.setPageSize(postPage.getSize());
        postResponse.setTotalElements(postPage.getTotalElements());
        postResponse.setTotalPages(postPage.getTotalPages());
        postResponse.setLast(postPage.isLast());
        return postResponse;
    }

    private Post findPostById(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Post", "id", id)
        );
    }

    private Category findCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Category", "id", id)
        );
    }

    private PostDto mapToDTO(Post post) {
        PostDto postDto = mapper.map(post, PostDto.class);
//        postDto.setId(post.getId());
//        postDto.setTitle(post.getTitle());
//        postDto.setDescription(post.getDescription());
//        postDto.setContent(post.getContent());
        return postDto;
    }

    private Post mapToEntity(PostDto postDto) {
        return mapper.map(postDto, Post.class);
    }
}

package com.shehan.blog.blogApp.service.impl;

import com.shehan.blog.blogApp.exception.ResourceNotFoundException;
import com.shehan.blog.blogApp.model.Post;
import com.shehan.blog.blogApp.payload.CommentDto;
import com.shehan.blog.blogApp.payload.PostDTO;
import com.shehan.blog.blogApp.payload.PostResponse;
import com.shehan.blog.blogApp.repository.PostRepository;
import com.shehan.blog.blogApp.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;
    private ModelMapper modelMapper;
    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDTO createPost(PostDTO postDTO) {
        Post post = maPDTOToPost(postDTO);
        Post postSaved = postRepository.save(post);
        PostDTO postResponse = mapPostTOPostDTO(postSaved);
        return postResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> postList = posts.getContent();
        List<PostDTO> postContent = postList.stream().map(post -> mapPostTOPostDTO(post)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postContent);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElemets(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDTO getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapPostTOPostDTO(post);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        post.setContent(postDTO.getContent());
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        PostDTO postDTOSaved = mapPostTOPostDTO(postRepository.save(post));
        return postDTOSaved;
    }

    @Override
    public void deletePostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }

    private PostDTO mapPostTOPostDTO(Post post){

        PostDTO postDTO = modelMapper.map(post, PostDTO.class);
        postDTO.setCommentDtos(post.getComments().stream().map(comment -> modelMapper.map(comment, CommentDto.class)).collect(Collectors.toSet()));
        return postDTO;
    }

    private Post maPDTOToPost(PostDTO postDTO){
        Post post = modelMapper.map(postDTO, Post.class);
        return post;
    }
}

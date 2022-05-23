package com.shehan.blog.blogApp.service.impl;

import com.shehan.blog.blogApp.exception.BlogApiException;
import com.shehan.blog.blogApp.exception.ResourceNotFoundException;
import com.shehan.blog.blogApp.model.Comment;
import com.shehan.blog.blogApp.model.Post;
import com.shehan.blog.blogApp.payload.CommentDto;
import com.shehan.blog.blogApp.payload.PostDTO;
import com.shehan.blog.blogApp.payload.PostResponse;
import com.shehan.blog.blogApp.repository.CommentRepository;
import com.shehan.blog.blogApp.repository.PostRepository;
import com.shehan.blog.blogApp.service.CommentService;
import com.shehan.blog.blogApp.service.PostService;
import javafx.geometry.Pos;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper modelMapper;
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        Comment comment = maPCommentDtoToComment(commentDto);
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);
        return mapCommentTOCommentDto(savedComment);
    }

    @Override
    public List<CommentDto> getCommentByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> mapCommentTOCommentDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "comment does not belong to the post");
        }

        return mapCommentTOCommentDto(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "comment does not belong to the post");
        }
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        Comment updatedComment = commentRepository.save(comment);
        return mapCommentTOCommentDto(updatedComment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "comment does not belong to the post");
        }
        commentRepository.delete(comment);
    }

    private CommentDto mapCommentTOCommentDto(Comment comment){
        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
        return commentDto;
    }

    private Comment maPCommentDtoToComment(CommentDto commentDto){
        Comment comment = modelMapper.map(commentDto, Comment.class);
        return comment;
    }
}

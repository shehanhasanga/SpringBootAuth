package com.shehan.blog.blogApp.service;

import com.shehan.blog.blogApp.payload.CommentDto;
import com.shehan.blog.blogApp.payload.PostDTO;
import com.shehan.blog.blogApp.payload.PostResponse;

import java.util.List;

public interface CommentService {
    CommentDto createComment(Long postId, CommentDto commentDto);
    List<CommentDto> getCommentByPostId(Long postId);
    CommentDto getCommentById(Long postId, Long CommnetId);
    CommentDto updateComment(Long postId, Long CommnetId, CommentDto commentDto);
    void deleteComment(Long postId, Long CommnetId);
}

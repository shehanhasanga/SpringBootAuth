package com.shehan.blog.blogApp.service;

import com.shehan.blog.blogApp.model.Post;
import com.shehan.blog.blogApp.payload.PostDTO;
import com.shehan.blog.blogApp.payload.PostResponse;
import javafx.geometry.Pos;

import java.security.acl.LastOwnerException;
import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);
    PostResponse getAllPosts(int pageNo , int pageSize, String sortBy, String sortDir);
    PostDTO getPostById(Long id);
    PostDTO updatePost(PostDTO postDTO, Long id);
    void deletePostById(Long id);
}

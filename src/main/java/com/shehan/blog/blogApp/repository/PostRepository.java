package com.shehan.blog.blogApp.repository;

import com.shehan.blog.blogApp.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}

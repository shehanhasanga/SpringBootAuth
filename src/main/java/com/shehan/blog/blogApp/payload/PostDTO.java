package com.shehan.blog.blogApp.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class PostDTO {
    private Long id;
    @NotNull
    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;
    @NotNull
    @Size(min = 10, message = "Post description should have at least 10 characters")
    private String description;
    @NotNull
    private String content;
    private Set<CommentDto> commentDtos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<CommentDto> getCommentDtos() {
        return commentDtos;
    }

    public void setCommentDtos(Set<CommentDto> commentDtos) {
        this.commentDtos = commentDtos;
    }
}

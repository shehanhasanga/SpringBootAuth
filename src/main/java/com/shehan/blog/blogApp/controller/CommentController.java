package com.shehan.blog.blogApp.controller;

import com.shehan.blog.blogApp.model.Comment;
import com.shehan.blog.blogApp.payload.CommentDto;
import com.shehan.blog.blogApp.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.smartcardio.CommandAPDU;
import javax.validation.Valid;
import java.lang.annotation.Retention;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment( @PathVariable(value = "postId") Long postId,@Valid @RequestBody CommentDto commentDto){
        return new ResponseEntity<CommentDto>(this.commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getComments(@PathVariable(value = "postId") Long postId){
        return commentService.getCommentByPostId(postId);
    }

    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getComments(@PathVariable(value = "postId") Long postId, @PathVariable(value = "commentId") Long commentId){
        return new ResponseEntity<CommentDto>(commentService.getCommentById(postId, commentId), HttpStatus.OK);
    }
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(value = "postId") Long postId, @PathVariable(value = "commentId") Long commentId,@Valid  @RequestBody CommentDto commentDto){
        return new ResponseEntity<CommentDto>(this.commentService.updateComment(postId,commentId, commentDto), HttpStatus.OK);
    }
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComments(@PathVariable(value = "postId") Long postId, @PathVariable(value = "commentId") Long commentId){
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<String>("Comment deleted successfully", HttpStatus.OK);
    }
}

package com.shehan.blog.blogApp.controller;

import com.shehan.blog.blogApp.payload.PostDTO;
import com.shehan.blog.blogApp.payload.PostResponse;
import com.shehan.blog.blogApp.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.shehan.blog.blogApp.utils.Constants.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO){
        return new ResponseEntity<PostDTO>(postService.createPost(postDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PostResponse> getPosts( @RequestParam(value = "pageNo", defaultValue = DEFAULT_PAGE_NUMBER , required = false) int pageNo,
                                                   @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                  @RequestParam(value = "sortBy", defaultValue = DEFAULT_PAGE_SORT_ITEM, required = false) String sortBy,
                                                  @RequestParam(value = "sortDir", defaultValue = DEFAULT_PAGE_SORT_DIRECTION, required = false) String sortDir
                                                   ){
        return new ResponseEntity<PostResponse>(postService.getAllPosts(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostsById(@PathVariable(name = "id") Long id){
        return new ResponseEntity<PostDTO>(postService.getPostById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO postDTO, @PathVariable(name = "id") Long id){
        PostDTO postDTOSaved = postService.updatePost(postDTO, id);
        return new ResponseEntity<PostDTO>(postDTOSaved, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") Long id){
        postService.deletePostById(id);
        return new ResponseEntity<String>("Post entity deleted successfully.", HttpStatus.OK);
    }
}

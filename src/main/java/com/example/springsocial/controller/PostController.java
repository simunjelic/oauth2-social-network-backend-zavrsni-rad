package com.example.springsocial.controller;

import com.example.springsocial.model.Post;
import com.example.springsocial.model.User;
import com.example.springsocial.payload.AuthResponse;
import com.example.springsocial.payload.LoginRequest;
import com.example.springsocial.payload.PostResponse;
import com.example.springsocial.security.CurrentUser;
import com.example.springsocial.security.UserPrincipal;
import com.example.springsocial.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("/addPost")
    public ResponseEntity<?> addPost(@RequestBody Post post,@CurrentUser UserPrincipal userPrincipal) throws NullPointerException {
        Post savedPost = postService.savePost(userPrincipal.getId(),post.getContent());
        return ResponseEntity.created(URI.create("/private/mypost")).body(savedPost);
    }
    @GetMapping("/getPosts")
    public ResponseEntity<?> getPosts(@CurrentUser UserPrincipal userPrincipal) throws NullPointerException {
        List<Post> list = postService.getPosts(userPrincipal.getId());
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
    @GetMapping("/delete/post")
    public ResponseEntity<?> searchUsers(@RequestParam("id") Long id){

       String message = postService.deletePost(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(message);
    }
    @PostMapping("/edit/post")
    public ResponseEntity<?> editPost(@RequestBody PostResponse postResponse) {

        String message = postService.editPost(postResponse);
        return ResponseEntity.ok(message);
    }

}

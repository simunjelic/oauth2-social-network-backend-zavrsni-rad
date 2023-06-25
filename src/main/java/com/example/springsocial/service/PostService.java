package com.example.springsocial.service;

import com.example.springsocial.model.Friend;
import com.example.springsocial.model.Post;
import com.example.springsocial.model.User;
import com.example.springsocial.payload.PostResponse;
import com.example.springsocial.repository.FriendRepository;
import com.example.springsocial.repository.PostRepository;
import com.example.springsocial.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    FriendRepository friendRepository;

    public Post savePost(Long id, String content) {
        Post post = new Post();
        User user = userRepository.findById(id).orElseThrow();
        post.setUser(user);
        post.setContent(content);
        return postRepository.save(post);
    }

    public List<Post> getPosts(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        List<Post> list = postRepository.findAllByOrderByCreatedDateDesc();;
       List<Friend> friends = friendRepository.findByFirstUser(user);
       List<Post> newList = new ArrayList<>();
        for (Post post:list) {
            int check = 0;
            for (Friend usr:friends) {
                if((post.getUser().getId()==usr.getSecondUser().getId())){
                   check++;
                   break;
                }

            }
            if(check>0 || post.getUser().getId()==user.getId()) newList.add(post);
        }
        return newList;
    }

    public String deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow();
        if(post!=null){
            postRepository.delete(post);
            return "Post deleted";
        }
        return "Cant found post";
    }

    public String editPost(PostResponse postResponse) {
        Post post = postRepository.findById(postResponse.getId()).orElseThrow();
        if(post!=null){
            post.setContent(postResponse.getContent());
            postRepository.save(post);
            return "Post edited";
        }
        return "Can't find post";
    }
}

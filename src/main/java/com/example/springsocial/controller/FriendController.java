package com.example.springsocial.controller;

import com.example.springsocial.model.Friend;
import com.example.springsocial.model.User;
import com.example.springsocial.security.CurrentUser;
import com.example.springsocial.security.UserPrincipal;
import com.example.springsocial.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FriendController {
    @Autowired
    private FriendService friendService;

    //start following someone
    @GetMapping("/friend/follow")
    public ResponseEntity<?> followFrined(@RequestParam("id") Long id, @CurrentUser UserPrincipal userPrincipal){
            String message = friendService.addFriend(userPrincipal.getId(),id);


        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
    @GetMapping("/friend/search")
    public ResponseEntity<?> searchFriend(@CurrentUser UserPrincipal userPrincipal){
        List<User> list = friendService.searchUserFriends(userPrincipal.getId());


        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
    @GetMapping("/friend/unfollow")
    public ResponseEntity<?> unfollowFriend(@RequestParam("id") Long id, @CurrentUser UserPrincipal userPrincipal){
        String message = friendService.unfollowFriend(userPrincipal.getId(),id);


        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
    //followers
    @GetMapping("/follower/search")
    public ResponseEntity<?> searchFollowers(@CurrentUser UserPrincipal userPrincipal){
        List<User> list = friendService.searchFollowers(userPrincipal.getId());


        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
    @GetMapping("/remove/follower")
    public ResponseEntity<?> removeFollower(@RequestParam("id") Long id, @CurrentUser UserPrincipal userPrincipal){
        String message = friendService.unfollowFriend(id,userPrincipal.getId());


        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
}

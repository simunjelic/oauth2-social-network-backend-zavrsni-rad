package com.example.springsocial.service;

import com.example.springsocial.model.Friend;
import com.example.springsocial.model.User;
import com.example.springsocial.repository.FriendRepository;
import com.example.springsocial.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FriendService {
    @Autowired
    FriendRepository friendRepository;
    @Autowired
    UserRepository userRepository;

    public String addFriend(Long firstId, Long secondId) {
        User first = userRepository.findById(firstId).orElseThrow();
        User second = userRepository.findById(secondId).orElseThrow();
        if(first!=null && second!=null){
            Friend friend = new Friend();
            if(!(friendRepository.existsByFirstUserAndSecondUser(first,second))&& first.getId()!= second.getId())
            {
                friend.setCreatedDate(new Date());
                friend.setFirstUser(first);
                friend.setSecondUser(second);
                friendRepository.save(friend);
                return second.getName()+" followed successfully";
            }
        }
        return "Error";
    }

    public List<User> searchUserFriends(Long id) {
        User user = userRepository.findById(id).orElseThrow();

        if(user!=null){

            List<Friend> list =friendRepository.findByFirstUser(user);
            List<User> userList = new ArrayList<>();
            for (Friend friend : list) {
                userList.add(friend.getSecondUser());
            }
            return userList;
        }
        return null;
    }

    public String unfollowFriend(Long firstId, Long secondId) {
        User first = userRepository.findById(firstId).orElseThrow();
        User second = userRepository.findById(secondId).orElseThrow();
        Friend friend = friendRepository.findByFirstUserAndSecondUser(first,second);
        if(friend!=null){
            friendRepository.deleteFollower(friend.getId());
            return second.getName()+" removed";
        }
        return "Error, cant' remove " + second.getName();

    }

    public List<User> searchFollowers(Long id) {
        User user = userRepository.findById(id).orElseThrow();

        if(user!=null){

            List<Friend> list =friendRepository.findBySecondUser(user);
            List<User> userList = new ArrayList<>();
            for (Friend friend : list) {
                userList.add(friend.getFirstUser());
            }
            return userList;
        }
        return null;
    }
}

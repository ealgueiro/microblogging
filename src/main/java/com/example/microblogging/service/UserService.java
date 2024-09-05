package com.example.microblogging.service;

import com.example.microblogging.model.User;
import com.example.microblogging.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void followUser(User user, User toFollow) {
        user.getFollowing().add(toFollow);
        userRepository.save(user);
    }

}
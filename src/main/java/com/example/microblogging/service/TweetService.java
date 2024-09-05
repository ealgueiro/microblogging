package com.example.microblogging.service;

import com.example.microblogging.model.Tweet;
import com.example.microblogging.model.User;
import com.example.microblogging.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TweetService {

    @Autowired
    private TweetRepository tweetRepository;

    public Tweet saveTweet(User user, String content) {
        Tweet tweet = new Tweet();
        tweet.setUser(user);
        tweet.setContent(content);
        tweet.setCreatedAt(LocalDateTime.now());
        return tweetRepository.save(tweet);
    }

    public List<Tweet> getTimelineForUser(User user) {
        return tweetRepository.findByUserInOrderByCreatedAtDesc(user.getFollowing());
    }
}
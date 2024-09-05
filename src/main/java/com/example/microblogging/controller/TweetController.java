package com.example.microblogging.controller;

import com.example.microblogging.exception.EntityNotFoundException;
import com.example.microblogging.exception.TweetTooLongException;
import com.example.microblogging.model.Tweet;
import com.example.microblogging.model.User;
import com.example.microblogging.model.dto.FollowDto;
import com.example.microblogging.model.dto.PostTweetDto;
import com.example.microblogging.service.TweetService;
import com.example.microblogging.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TweetController {

    private static final int MAX_TWEET_LENGTH = 280;
    @Autowired
    private TweetService tweetService;

    @Autowired
    private UserService userService;

    @PostMapping("/tweet")
    public ResponseEntity<Tweet> postTweet(@RequestBody PostTweetDto dto) {
        User user = Optional.ofNullable(userService.findUserByUsername(dto.getUsername())).orElseThrow(() -> new EntityNotFoundException("User not found: " + dto.getUsername()));

        if (dto.getContent().length() > MAX_TWEET_LENGTH) {
            throw new TweetTooLongException();
        }

        Tweet tweet = tweetService.saveTweet(user, dto.getContent());

        return new ResponseEntity<>(tweet, HttpStatus.CREATED);
    }

    @PostMapping("/follow")
    public ResponseEntity<?> followUser(@RequestBody FollowDto dto) {
        User user = Optional.ofNullable(userService.findUserByUsername(dto.getUsername())).orElseThrow(() -> new EntityNotFoundException("User not found: " + dto.getUsername()));
        User userToFollow = Optional.ofNullable(userService.findUserByUsername(dto.getToFollow())).orElseThrow(() -> new EntityNotFoundException("User to follow not found: " + dto.getToFollow()));

        userService.followUser(user, userToFollow);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/timeline")
    public ResponseEntity<List<Tweet>> getTimeline(@RequestParam("username") String username) {
        User user = Optional.ofNullable(userService.findUserByUsername(username)).orElseThrow(() -> new EntityNotFoundException("User not found: " + username));

        List<Tweet> timeline = tweetService.getTimelineForUser(user);

        return new ResponseEntity<>(timeline, HttpStatus.OK);
    }
}
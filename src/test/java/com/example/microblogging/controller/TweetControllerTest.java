package com.example.microblogging.controller;

import com.example.microblogging.exception.EntityNotFoundException;
import com.example.microblogging.exception.TweetTooLongException;
import com.example.microblogging.model.Tweet;
import com.example.microblogging.model.User;
import com.example.microblogging.model.dto.FollowDto;
import com.example.microblogging.model.dto.PostTweetDto;
import com.example.microblogging.service.TweetService;
import com.example.microblogging.service.UserService;
import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class TweetControllerTest {

    @Mock
    private TweetService tweetService;

    @Mock
    private UserService userService;

    @InjectMocks
    private TweetController tweetController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPostTweet() {
        PostTweetDto dto = new PostTweetDto();
        dto.setUsername("user1");
        dto.setContent("This is a tweet");

        User user1 = new User();
        user1.setUsername("user1");

        when(userService.findUserByUsername("user1")).thenReturn(user1);

        ResponseEntity<Tweet> response = tweetController.postTweet(dto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testPostTweet_TweetToLong() {
        PostTweetDto dto = new PostTweetDto();
        dto.setUsername("user1");
        dto.setContent(RandomString.make(300));

        User user1 = new User();
        user1.setUsername("user1");

        when(userService.findUserByUsername("user1")).thenReturn(user1);

        assertThrows(TweetTooLongException.class, () -> tweetController.postTweet(dto));
    }

    @Test
    void testFollowUser() {
        FollowDto dto = new FollowDto();
        dto.setUsername("user1");
        dto.setToFollow("user2");

        User user1 = new User();
        user1.setUsername("user1");

        User user2 = new User();
        user2.setUsername("user2");

        when(userService.findUserByUsername("user1")).thenReturn(user1);
        when(userService.findUserByUsername("user2")).thenReturn(user2);

        ResponseEntity<?> response = tweetController.followUser(dto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testFollowUser_ToFollow_EntityNotFound() {
        FollowDto dto = new FollowDto();
        dto.setUsername("user1");
        dto.setToFollow("user2");

        User user1 = new User();
        user1.setUsername("user1");

        when(userService.findUserByUsername("user1")).thenReturn(user1);
        when(userService.findUserByUsername("user2")).thenThrow(new EntityNotFoundException("User not found"));

        assertThrows(EntityNotFoundException.class, () -> tweetController.followUser(dto));
    }

    @Test
    void testFollowUser_Username_EntityNotFound() {
        FollowDto dto = new FollowDto();
        dto.setUsername("user1");
        dto.setToFollow("user2");

        when(userService.findUserByUsername("user1")).thenThrow(new EntityNotFoundException("User not found"));

        assertThrows(EntityNotFoundException.class, () -> tweetController.followUser(dto));
    }

    @Test
    void testGetTimeline() {

        when(userService.findUserByUsername("user1")).thenThrow(new EntityNotFoundException("User not found"));

        assertThrows(EntityNotFoundException.class, () -> tweetController.getTimeline("user1"));
    }

    @Test
    void testGetTimeline_EntityNotFound() {

        when(userService.findUserByUsername("user1")).thenThrow(new EntityNotFoundException("User not found"));

        assertThrows(EntityNotFoundException.class, () -> tweetController.getTimeline("user1"));
    }

}
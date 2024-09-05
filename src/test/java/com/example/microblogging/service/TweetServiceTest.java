package com.example.microblogging.service;

import com.example.microblogging.model.Tweet;
import com.example.microblogging.model.User;
import com.example.microblogging.repository.TweetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TweetServiceTest {

    @Mock
    private TweetRepository tweetRepository;

    @InjectMocks
    private TweetService tweetService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveTweet() {
        User user = new User();
        user.setUsername("user1");

        Tweet mockTweet = new Tweet();
        mockTweet.setContent("Hello World!");
        mockTweet.setUser(user);
        mockTweet.setCreatedAt(LocalDateTime.now());

        when(tweetRepository.save(any(Tweet.class))).thenReturn(mockTweet);

        Tweet savedTweet = tweetService.saveTweet(user, "Hello World!");

        assertNotNull(savedTweet);
        assertEquals("Hello World!", savedTweet.getContent());
        assertEquals("user1", savedTweet.getUser().getUsername());
    }

    @Test
    void testGetTimelineForUser() {

        User user1 = new User();
        user1.setUsername("user1");

        Tweet tweet1 = new Tweet();
        tweet1.setContent("Tweet 1");
        tweet1.setCreatedAt(LocalDateTime.now());

        Tweet tweet2 = new Tweet();
        tweet2.setContent("Tweet 2");
        tweet2.setCreatedAt(LocalDateTime.now().minusMinutes(5));

        when(tweetRepository.findByUserInOrderByCreatedAtDesc(anySet())).thenReturn(List.of(tweet1, tweet2));

        List<Tweet> tweets = tweetService.getTimelineForUser(user1);

        assertEquals(2, tweets.size());
        assertEquals("Tweet 1", tweets.get(0).getContent());
        assertEquals("Tweet 2", tweets.get(1).getContent());
    }

}
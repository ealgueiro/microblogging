package com.example.microblogging.service;

import com.example.microblogging.model.User;
import com.example.microblogging.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindUserByUsername() {
        User mockUser = new User();
        mockUser.setUsername("user1");

        when(userRepository.findByUsername("user1")).thenReturn(mockUser);

        User user = userService.findUserByUsername("user1");
        assertNotNull(user);
        assertEquals("user1", user.getUsername());
    }

    @Test
    void testFollowUser() {
        User user1 = new User();
        user1.setUsername("user1");

        User user2 = new User();
        user2.setUsername("user2");

        userService.followUser(user1, user2);

        verify(userRepository, times(1)).save(user1);
        assertTrue(user1.getFollowing().contains(user2));
    }
}
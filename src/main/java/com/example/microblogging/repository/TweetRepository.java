package com.example.microblogging.repository;

import com.example.microblogging.model.Tweet;
import com.example.microblogging.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface TweetRepository extends JpaRepository<Tweet, Long> {
    List<Tweet> findByUserOrderByCreatedAtDesc(User user);

    List<Tweet> findByUserInOrderByCreatedAtDesc(Set<User> users);
}
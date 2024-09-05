package com.example.microblogging.model.dto;

import lombok.Data;

@Data
public class FollowDto {
    private String username;
    private String toFollow;
}

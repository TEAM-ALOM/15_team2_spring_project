package com.example.qwer.user.dto;

import com.example.qwer.user.User;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private final Long id;
    private final String username;
    private final String email;

    public UserResponseDto(User user) {
        this.id = user.getUserId();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}

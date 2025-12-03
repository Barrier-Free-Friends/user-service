package org.bf.userservice.application.dto;

import org.bf.userservice.domain.User;

import java.util.UUID;

public record UserResponse(
        UUID userId,
        String username,
        String email,
        String nickname
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getNickname()
        );
    }
}

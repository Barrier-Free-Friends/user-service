package org.bf.userservice.application.dto;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {
}

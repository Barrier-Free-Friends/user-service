package org.bf.userservice.application.dto;

public record SignUpRequest(
        String username,
        String email,
        String password,
        String confirmPassword,
        String nickname
) {
}

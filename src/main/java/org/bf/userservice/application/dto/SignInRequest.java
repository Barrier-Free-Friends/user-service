package org.bf.userservice.application.dto;

public record SignInRequest(
        String username,
        String password
) {
}

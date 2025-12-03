package org.bf.userservice.application.dto;

import jakarta.validation.constraints.NotBlank;

public record SignInRequest(
        @NotBlank(message = "아이디 또는 이메일은 필수입니다.")
        String usernameOrEmail,

        @NotBlank(message = "비밀번호 입력은 필수입니다.")
        String password
) {
}

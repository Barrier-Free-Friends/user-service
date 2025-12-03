package org.bf.userservice.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.bf.userservice.domain.UserErrorCode;
import org.bf.userservice.global.infrastructure.exception.CustomException;

import java.util.Objects;

public record SignUpRequest(
        @NotBlank(message = "사용자 아이디는 필수입니다.")
        @Size(min = 4, max = 20, message = "사용자 아이디는 4자 이상 20자 이하입니다.")
        String username,

        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "유효하지 않은 이메일입니다.")
        String email,

        @NotBlank(message = "비밀번호는 필수입니다.")
        @Size(min = 8, max = 30, message = "비밀번호는 8자 이상 30자 이하입니다.")
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{8,30}$",
                message = "비밀번호는 8자 이상, 30자 이하이며, 최소 하나의 대문자와 특수문자를 포함해야 합니다."
        )
        String password,

        @NotBlank(message = "비밀번호 확인은 필수입니다.")
        String confirmPassword,

        @Size(min = 2, max = 15, message = "닉네임은 2자 이상 15자 이하입니다.")
        String nickname
) {
    public SignUpRequest {
        // 비밀번호, 비밀번호 확인 일치 검증
        if (!Objects.equals(password, confirmPassword)) {
            throw new CustomException(UserErrorCode.PASSWORD_NOT_CONFIRM);
        }
    }
}

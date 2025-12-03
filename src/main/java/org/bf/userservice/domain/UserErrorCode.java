package org.bf.userservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bf.userservice.global.infrastructure.error.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements BaseErrorCode {
    USERNAME_DUPLICATE(HttpStatus.BAD_REQUEST, "USERNAME400", "이미 존재하는 아이디입니다."),
    EMAIL_DUPLICATE(HttpStatus.BAD_REQUEST, "EMAIL400", "이미 존재하는 이메일입니다."),
    PASSWORD_NOT_CONFIRM(HttpStatus.BAD_REQUEST, "PASSWORD400", "비밀번호와 비밀번호 확인이 일치하지 않습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER404", "아이디 또는 이메일로 가입된 회원이 없습니다."),
    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "PASSWORD_MATCH_400", "비밀번호가 일치하지 않습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}

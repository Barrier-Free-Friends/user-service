package org.bf.userservice.application;

import org.bf.userservice.application.dto.SignInRequest;
import org.bf.userservice.application.dto.SignUpRequest;
import org.bf.userservice.application.dto.TokenResponse;
import org.bf.userservice.application.dto.UserResponse;

public interface AuthenticationService {

    // 회원가입
    UserResponse signUp(SignUpRequest signUpRequest);

    // 로그인
    TokenResponse signIn(SignInRequest signInRequest);
}

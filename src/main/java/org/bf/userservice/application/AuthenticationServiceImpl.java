package org.bf.userservice.application;

import lombok.RequiredArgsConstructor;
import org.bf.userservice.application.dto.SignInRequest;
import org.bf.userservice.application.dto.SignUpRequest;
import org.bf.userservice.application.dto.TokenResponse;
import org.bf.userservice.application.dto.UserResponse;
import org.bf.userservice.domain.User;
import org.bf.userservice.domain.UserErrorCode;
import org.bf.userservice.domain.UserRepository;
import org.bf.userservice.global.infrastructure.exception.CustomException;
import org.bf.userservice.infrastructure.security.jwt.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 회원가입
     * */
    @Override
    public UserResponse signUp(SignUpRequest signUpRequest) {
        // username 중복 확인
        userRepository.findByUsername(signUpRequest.username()).ifPresent(user -> {
            throw new CustomException(UserErrorCode.USERNAME_DUPLICATE);
        });
        // email 중복 확인
        userRepository.findByEmail(signUpRequest.email()).ifPresent(user -> {
            throw new CustomException(UserErrorCode.EMAIL_DUPLICATE);
        });

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(signUpRequest.password());

        User user = User.builder()
                .username(signUpRequest.username())
                .email(signUpRequest.email())
                .password(encodedPassword)
                .nickname(signUpRequest.nickname())
                .build();
        userRepository.save(user);
        return UserResponse.from(user);
    }

    /**
     * 로그인
     * @return accessToken, refreshToken
     * */
    @Override
    public TokenResponse signIn(SignInRequest signInRequest) {
        // 사용자 이메일 또는 아이디로 조회
        User user = userRepository.findByEmailOrUsernameAndDeletedAtIsNull(
                signInRequest.usernameOrEmail(), signInRequest.usernameOrEmail()
        ).orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));

        // 비밀번호 검증
        if (!passwordEncoder.matches(signInRequest.password(), user.getPassword())) {
            throw new CustomException(UserErrorCode.PASSWORD_NOT_MATCH);
        }

        // 토큰 생성 및 발급
        String accessToken = jwtTokenProvider.createAccessToken(user.getUserId(), user.getUsername(), user.getEmail(), List.of("ROLE_USER"));
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getUserId());

        return new TokenResponse(accessToken, refreshToken);
    }
}

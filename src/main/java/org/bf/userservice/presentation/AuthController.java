package org.bf.userservice.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bf.global.infrastructure.CustomResponse;
import org.bf.userservice.application.AuthenticationService;
import org.bf.userservice.application.dto.SignInRequest;
import org.bf.userservice.application.dto.SignUpRequest;
import org.bf.userservice.application.dto.TokenResponse;
import org.bf.userservice.application.dto.UserResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원가입, 로그인 API")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    public CustomResponse<UserResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        return CustomResponse.onSuccess(authenticationService.signUp(signUpRequest));
    }

    @Operation(summary = "로그인")
    @PostMapping("/signin")
    public CustomResponse<TokenResponse> signIn(@Valid @RequestBody SignInRequest signInRequest) {
        return CustomResponse.onSuccess(authenticationService.signIn(signInRequest));
    }
}

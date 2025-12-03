package org.bf.userservice.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bf.userservice.application.AuthenticationService;
import org.bf.userservice.application.dto.SignInRequest;
import org.bf.userservice.application.dto.SignUpRequest;
import org.bf.userservice.application.dto.TokenResponse;
import org.bf.userservice.application.dto.UserResponse;
import org.bf.userservice.global.infrastructure.CustomResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public CustomResponse<UserResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        return CustomResponse.onSuccess(authenticationService.signUp(signUpRequest));
    }

    @PostMapping("/signin")
    public CustomResponse<TokenResponse> signIn(@Valid @RequestBody SignInRequest signInRequest) {
        return CustomResponse.onSuccess(authenticationService.signIn(signInRequest));
    }
}

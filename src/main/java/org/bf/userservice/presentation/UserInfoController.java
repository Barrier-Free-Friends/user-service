package org.bf.userservice.presentation;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.bf.global.infrastructure.CustomResponse;
import org.bf.global.security.SecurityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserInfoController {

    private final SecurityUtils securityUtils;

    @Operation(summary = "로그인한 사용자의 userId와 username 반환")
    @GetMapping("/info")
    public CustomResponse<?> info() {
        return CustomResponse.onSuccess(securityUtils.getCurrentUserId() + " : " + securityUtils.getCurrentUsername());
    }

}

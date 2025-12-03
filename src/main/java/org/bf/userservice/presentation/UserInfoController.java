package org.bf.userservice.presentation;

import lombok.RequiredArgsConstructor;
import org.bf.global.infrastructure.CustomResponse;
import org.bf.global.security.SecurityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserInfoController {

    private final SecurityUtils securityUtils;

    @GetMapping("/info")
    public CustomResponse<?> info() {
        return CustomResponse.onSuccess(securityUtils.getCurrentUserId() + " : " + securityUtils.getCurrentUsername());
    }

}

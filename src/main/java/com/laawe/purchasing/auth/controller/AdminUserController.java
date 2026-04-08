package com.laawe.purchasing.auth.controller;

import com.laawe.purchasing.auth.model.request.ChangePasswordRequest;
import com.laawe.purchasing.auth.model.request.UserRegisterRequest;
import com.laawe.purchasing.auth.model.response.GenericApiResponse;
import com.laawe.purchasing.auth.service.AdminUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.laawe.purchasing.auth.config.constant.AppConstant.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_API_URL)
public class AdminUserController {

    private final AdminUserService adminUserService;

    @GetMapping(PROFILE_API)
    public ResponseEntity<GenericApiResponse<?>> getProfile(
            @RequestHeader(name = HEADER_X_USER_ID) String loggedInUserIdf
    ) {
        return ResponseEntity.ok(adminUserService.getProfile(loggedInUserIdf));
    }

    @PostMapping(REGISTER_API)
    public ResponseEntity<GenericApiResponse<?>> getRegister(
            @Valid @RequestBody UserRegisterRequest request,
            @RequestHeader(name = HEADER_X_USER_ID) String loggedInUserIdf
    ) {
        return ResponseEntity.ok(adminUserService.getRegister(request, loggedInUserIdf));
    }

    @PatchMapping(CHANGE_PASSWORD_API)
    public ResponseEntity<GenericApiResponse<?>> changePassword(
            @Valid @RequestBody ChangePasswordRequest changePasswordRequest,
            @RequestHeader(name = HEADER_X_USER_ID) String loggedInUserIdf
    ) {
        return ResponseEntity.ok(adminUserService.changePassword(changePasswordRequest, loggedInUserIdf));
    }

}

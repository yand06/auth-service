package com.laawe.purchasing.auth.controller;

import com.laawe.purchasing.auth.model.response.GenericApiResponse;
import com.laawe.purchasing.auth.service.AdminUserService;
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

}

package com.laawe.purchasing.auth.controller;

import com.laawe.purchasing.auth.model.request.LoginRequest;
import com.laawe.purchasing.auth.model.request.LogoutRequest;
import com.laawe.purchasing.auth.model.response.GenericApiResponse;
import com.laawe.purchasing.auth.model.response.LoginResponse;
import com.laawe.purchasing.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.laawe.purchasing.auth.config.constant.AppConstant.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_API_URL)
public class AuthController {

    private final AuthService authService;

    @PostMapping(LOGIN_API)
    public ResponseEntity<GenericApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping(LOGOUT_API)
    public ResponseEntity<GenericApiResponse<?>> logout(@Valid @RequestBody LogoutRequest logoutRequest, HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(authService.logout(logoutRequest, httpServletRequest));
    }

}

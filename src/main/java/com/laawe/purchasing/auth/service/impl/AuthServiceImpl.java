package com.laawe.purchasing.auth.service.impl;

import com.laawe.purchasing.auth.config.constant.ResponseCode;
import com.laawe.purchasing.auth.controller.handler.BusinessException;
import com.laawe.purchasing.auth.model.entity.M_User;
import com.laawe.purchasing.auth.model.request.LoginRequest;
import com.laawe.purchasing.auth.model.request.LogoutRequest;
import com.laawe.purchasing.auth.model.response.GenericApiResponse;
import com.laawe.purchasing.auth.model.response.LoginResponse;
import com.laawe.purchasing.auth.repository.UserRepository;
import com.laawe.purchasing.auth.service.AuthService;
import com.laawe.purchasing.auth.service.JWTService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;

    @Override
    public GenericApiResponse<LoginResponse> login(LoginRequest loginRequest) {
        // 1. Find user in Database
        M_User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new BusinessException(ResponseCode.USER_NOT_FOUND, "USER NOT FOUND"));

        // 2. Check if password is correct
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new BusinessException(ResponseCode.INVALID_CREDENTIALS);
        }

        // 3. Retrieve the role_name from the Role table
        String roleName = user.getRole().getName();

        // 4. Setup claims for the JWT
        Map<String, Object> claims = Map.of(
                "user_id", user.getIdf().toString(),
                "roles", roleName
        );

        // 5. Generate Token
        String token = jwtService.generateToken(user.getUsername(), claims);
        LoginResponse data = LoginResponse.builder()
                .userIdf(user.getIdf().toString())
                .userName(user.getUsername())
                .userFullName(user.getFullName())
                .userRole(user.getRole().getName())
                .token(token)
                .build();

        // 6. Return response DTO
        return GenericApiResponse.success(data, "LOGIN SUCCESSFULLY");
    }

    @Override
    public GenericApiResponse<Object> logout(LogoutRequest logoutRequest, HttpServletRequest httpServletRequest) {
        jwtService.revokeAuthJwtToken(httpServletRequest);
        return GenericApiResponse.success(null, "LOGOUT SUCCESSFULLY");
    }

}

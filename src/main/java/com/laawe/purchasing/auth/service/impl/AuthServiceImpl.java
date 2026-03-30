package com.laawe.purchasing.auth.service.impl;

import com.laawe.purchasing.auth.config.constant.ResponseCode;
import com.laawe.purchasing.auth.controller.handler.BusinessException;
import com.laawe.purchasing.auth.model.entity.M_User;
import com.laawe.purchasing.auth.model.request.LoginRequest;
import com.laawe.purchasing.auth.model.request.LogoutRequest;
import com.laawe.purchasing.auth.model.request.RefreshTokenRequest;
import com.laawe.purchasing.auth.model.response.GenericApiResponse;
import com.laawe.purchasing.auth.model.response.LoginResponse;
import com.laawe.purchasing.auth.model.response.TokenInfo;
import com.laawe.purchasing.auth.repository.UserRepository;
import com.laawe.purchasing.auth.service.AuthService;
import com.laawe.purchasing.auth.service.JWTService;
import com.laawe.purchasing.auth.utility.TimeHelper;
import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static com.laawe.purchasing.auth.config.constant.AppConstant.SEVEN_DAYS_IN_MILLISECONDS;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public GenericApiResponse<LoginResponse> getLogin(LoginRequest loginRequest) {
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
        TokenInfo token = jwtService.generateToken(user.getUsername(), claims);
        TokenInfo refreshToken = jwtService.generateRefreshToken(user.getUsername(), claims);
        Instant userExpiresIn = TimeHelper.toInstant(token.expiresTime());
        Instant refreshTokenExpiresIn = TimeHelper.toInstant(refreshToken.expiresTime());

        LoginResponse data = new LoginResponse()
                .setUserIdf(user.getIdf())
                .setUserName(user.getUsername())
                .setUserFullName(user.getFullName())
                .setUserRole(user.getRole().getName())
                .setAccessToken(token.token())
                .setUserAccessExpiresIn(userExpiresIn)
                .setRefreshToken(refreshToken.token())
                .setRefreshExpiresIn(refreshTokenExpiresIn);

        redisTemplate.opsForValue().set(
                "ACTIVE_SESSION:" + user.getUsername().toUpperCase(),
                refreshToken.token(),
                Duration.ofMillis(SEVEN_DAYS_IN_MILLISECONDS)
        );

        // 6. Return response DTO
        return GenericApiResponse.success(data, "LOGIN SUCCESSFULLY");
    }

    @Override
    public GenericApiResponse<Object> getLogout(LogoutRequest logoutRequest, HttpServletRequest httpServletRequest) {
        jwtService.revokeAuthAccessToken(httpServletRequest);
        jwtService.revokeAuthRefreshToken(logoutRequest);
        return GenericApiResponse.success(null, "LOGOUT SUCCESSFULLY");
    }

    @Override
    public GenericApiResponse<LoginResponse> getRefreshToken(RefreshTokenRequest refreshTokenRequest) {
        JWTClaimsSet claimsSet = jwtService.validateAndGetClaims(refreshTokenRequest.getRefreshToken());
        String username = claimsSet.getSubject();
        M_User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException(ResponseCode.USER_NOT_FOUND, "USER NOT FOUND"));

        Map<String, Object> newClaims = new HashMap<>();
        newClaims.put("user_id", user.getIdf().toString());
        newClaims.put("roles", user.getRole().getName());

        TokenInfo newAccessToken = jwtService.generateToken(username, newClaims);
        Instant newAccessTokenExpiresIn = TimeHelper.toInstant(newAccessToken.expiresTime());

        LoginResponse data = new LoginResponse()
                .setUserIdf(user.getIdf())
                .setUserName(user.getUsername())
                .setUserFullName(user.getFullName())
                .setUserRole(user.getRole().getName())
                .setAccessToken(newAccessToken.token())
                .setUserAccessExpiresIn(newAccessTokenExpiresIn);

        return GenericApiResponse.success(data, "REFRESH TOKEN SUCCESSFULLY");
    }
}

package com.laawe.purchasing.auth.service;

import com.laawe.purchasing.auth.model.request.LogoutRequest;
import com.laawe.purchasing.auth.model.response.TokenInfo;
import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public interface JWTService {
    TokenInfo generateToken(String username, Map<String, Object> claims);
    TokenInfo generateRefreshToken(String username, Map<String, Object> claims);
    void revokeAuthAccessToken(HttpServletRequest serverHttpRequest);
    void revokeAuthRefreshToken(LogoutRequest logoutRequest);
    JWTClaimsSet validateAndGetClaims(String token);
}

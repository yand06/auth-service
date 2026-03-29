package com.laawe.purchasing.auth.service;

import com.laawe.purchasing.auth.model.response.TokenInfo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.Map;

public interface JWTService {
    TokenInfo generateToken(String username, Map<String, Object> claims);
    void revokeAuthJwtToken(HttpServletRequest serverHttpRequest);
}

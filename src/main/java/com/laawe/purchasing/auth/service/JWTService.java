package com.laawe.purchasing.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.Map;

public interface JWTService {
    String generateToken(String username, Map<String, Object> claims);
    void revokeAuthJwtToken(HttpServletRequest serverHttpRequest);
}

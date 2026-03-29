package com.laawe.purchasing.auth.service.impl;

import com.laawe.purchasing.auth.config.constant.ResponseCode;
import com.laawe.purchasing.auth.controller.handler.BusinessException;
import com.laawe.purchasing.auth.service.JWTService;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.time.Duration;
import java.util.Date;
import java.util.Map;

import static com.laawe.purchasing.auth.config.constant.AppConstant.*;

@RequiredArgsConstructor
@Slf4j
@Service
public class JWTServiceImpl implements JWTService {

    @Value("${jwt.secret}")
    private String secret;

    private final StringRedisTemplate redisTemplate;

    @Override
    public String generateToken(String username, Map<String, Object> claims) {
        try {
            JWSSigner signer = new MACSigner(secret);

            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(username)
                    .issuer("http://localhost:8081")
                    .issueTime(new Date())
                    .expirationTime(new Date(System.currentTimeMillis() + 3600000))
                    .claim("roles", claims.get("roles"))
                    .claim("user_id", claims.get("user_id"))
                    .build();

            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            signedJWT.sign(signer);

            return signedJWT.serialize();
        } catch (Exception e) {
            throw new RuntimeException("FAILED TO GENERATE TOKEN ---", e);
        }
    }

    @Override
    public void revokeAuthJwtToken(HttpServletRequest serverHttpRequest) {
        // Get token from header
        String token = resolveToken(serverHttpRequest);
        if (!StringUtils.hasText(token)) return;

        try {
            // Parse token
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();

            // Get JTI (JWT ID)
            String jti = claimsSet.getJWTID();
            if (!StringUtils.hasText(jti)) return;

            // Calculate sisa umur token (Time to Live)
            long expirationTime = claimsSet.getExpirationTime().getTime();
            long ttlMs = Math.max(0L, expirationTime - System.currentTimeMillis());

            // If token expired secara alami, tidak perlu di blacklist
            if (ttlMs <= 0) return;

            // Store to redis with ttl
            redisTemplate.opsForValue().set(BL_PREFIX + jti, EMPTY, Duration.ofMillis(ttlMs));

            log.info("TOKEN REVOKED SUCCESSFULLY --- jti = {} ttlMs = {}", jti, ttlMs);

        } catch (BusinessException businessException) {
            log.error("FAILED TO REVOKE TOKEN");
            throw new BusinessException(ResponseCode.BAD_REQUEST, "FAILED TO REVOKE TOKEN");
        } catch (ParseException e) {
            throw new BusinessException(ResponseCode.BAD_REQUEST, "FAILED TO REVOKE TOKEN --- " + e);
        }
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HEADER_AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(HEADER_BEARER)){
            return bearerToken.substring(7);
        }
        return null;
    }

}


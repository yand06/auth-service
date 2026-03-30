package com.laawe.purchasing.auth.service.impl;

import com.laawe.purchasing.auth.config.constant.ResponseCode;
import com.laawe.purchasing.auth.controller.handler.BusinessException;
import com.laawe.purchasing.auth.model.request.LogoutRequest;
import com.laawe.purchasing.auth.model.response.TokenInfo;
import com.laawe.purchasing.auth.service.JWTService;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.laawe.purchasing.auth.config.constant.AppConstant.*;

@RequiredArgsConstructor
@Slf4j
@Service
public class JWTServiceImpl implements JWTService {

    @Value("${jwt.secret}")
    private String secret;

    private final StringRedisTemplate redisTemplate;

    @Override
    public TokenInfo generateToken(String username, Map<String, Object> claims) {
        return buildAndSignToken(username, claims, ONE_HOURS_IN_MILLISECONDS);
    }

    @Override
    public TokenInfo generateRefreshToken(String username, Map<String, Object> claims) {
        Map<String, Object> refreshTokenClaims = new HashMap<>();
        refreshTokenClaims.put("user_id", claims.get("user_id"));

        return buildAndSignToken(username, refreshTokenClaims, SEVEN_DAYS_IN_MILLISECONDS);
    }

    @Override
    public void revokeAuthAccessToken(HttpServletRequest serverHttpRequest) {
        String token = resolveToken(serverHttpRequest);
        if (!StringUtils.hasText(token)) return;

        revokeToken(token);
    }

    @Override
    public void revokeAuthRefreshToken(LogoutRequest logoutRequest) {
        String token = logoutRequest.getSecurityKey();
        if (!StringUtils.hasText(token)) return;

        revokeToken(token);
    }

    private void revokeToken(String token){
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

    private TokenInfo buildAndSignToken(String username, Map<String, Object> claims, Long expirationMillis) {
        try {
            JWSSigner signer = new MACSigner(secret);

            // 1. Mulai merakit data bawaan
            JWTClaimsSet.Builder claimsBuilder = new JWTClaimsSet.Builder()
                    .jwtID(UUID.randomUUID().toString())
                    .subject(username)
                    .issuer("http://localhost:8081")
                    .issueTime(new Date())
                    .expirationTime(new Date(System.currentTimeMillis() + expirationMillis));

            // 2. Looping untuk memasukkan claims dinamis dari parameter
            if (claims != null) {
                for (Map.Entry<String, Object> entry : claims.entrySet()) {
                    claimsBuilder.claim(entry.getKey(), entry.getValue());
                }
            }

            // 3. Bangun objek klaim
            JWTClaimsSet claimsSet = claimsBuilder.build();
            Long expirationTime = claimsSet.getExpirationTime().getTime();

            // 4. Proses tanda tangan menggunakan algoritma HS256
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            signedJWT.sign(signer);

            return new TokenInfo(signedJWT.serialize(), expirationTime);

        } catch (Exception e) {
            throw new RuntimeException("FAILED TO GENERATE TOKEN --- ", e);
        }
    }

    @Override
    public JWTClaimsSet validateAndGetClaims(String token) {
        try {
            // 1. Parse string menjadi objek JWT
            SignedJWT signedJWT = SignedJWT.parse(token);

            // 2. Verifikasi tanda tangan menggunakan Secret Key
            JWSVerifier verifier = new MACVerifier(secret.getBytes());
            if (!signedJWT.verify(verifier)) {
                throw new BusinessException(ResponseCode.UNAUTHORIZED);
            }

            // 3. Cek apakah token sudah kedaluwarsa
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
            if (new Date().after(claims.getExpirationTime())) {
                throw new RuntimeException("Token sudah expired!");
            }

            // 4. Jika aman, kembalikan isi payload-nya
            return claims;

        } catch (Exception e) {
            throw new RuntimeException("Token tidak valid: " + e.getMessage());
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


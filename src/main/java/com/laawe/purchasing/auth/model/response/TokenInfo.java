package com.laawe.purchasing.auth.model.response;

public record TokenInfo(String token, Long expiresTime) {
}

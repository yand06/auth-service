package com.laawe.purchasing.auth.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.util.UUID;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse {
    private UUID userIdf;
    private String userName;
    private String userFullName;
    private String userRole;
    private String accessToken;
    private Instant userAccessExpiresIn;
    private String refreshToken;
    private Instant refreshExpiresIn;
}

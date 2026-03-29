package com.laawe.purchasing.auth.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileResponse {
    private UUID userIdf;
    private String userName;
    private String userFullName;
    private String userRoleName;
}

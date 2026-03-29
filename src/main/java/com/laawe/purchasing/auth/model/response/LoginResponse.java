package com.laawe.purchasing.auth.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private String userIdf;
    private String userName;
    private String userFullName;
    private String userRole;
    private String token;

}

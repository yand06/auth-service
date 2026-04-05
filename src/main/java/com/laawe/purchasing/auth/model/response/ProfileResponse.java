package com.laawe.purchasing.auth.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileResponse {
    private UUID userIdf;
    private String userName;
    private String userFullName;
    private String userEmail;
    private String userPhoneNumber;
    private String userStatus;
    private Boolean userIsAdmin;
    private String userRoleName;
    private String userEmployeeId;
    private String userDepartmentName;
    private String userAvatar;
    private LocalDateTime userJoinDate;
    private String userOfficeLocation;
}
package com.laawe.purchasing.auth.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ProfileResponse(
        UUID userIdf,
        String userName,
        String userFullName,
        String userEmail,
        String userPhoneNumber,
        String userStatus,
        Boolean userIsAdmin,
        String userRoleName,
        String userEmployeeId,
        String userDepartmentName,
        String userAvatar,
        LocalDateTime userJoinDate,
        String userOfficeLocation
) {
}
package com.laawe.purchasing.auth.model.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserProfileDTO(
        String departmentName,
        String avatar,
        String officeLocation,
        UUID userIdf,
        String fullName,
        String email,
        String employeeId,
        String username,
        String phoneNumber,
        Integer status,
        Boolean isAdmin,
        LocalDateTime createdAt, String roleName
){}

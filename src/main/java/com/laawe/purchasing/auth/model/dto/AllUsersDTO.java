package com.laawe.purchasing.auth.model.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record AllUsersDTO(
        UUID idf,
        String fullName,
        String email,
        String phoneNumber,
        String roleName,
        String employeeId,
        String username,
        Integer statusCode,
        Boolean isAdmin,
        LocalDateTime createdAt,
        String userDetailDepartmentName,
        String userOfficeLocation
) {
}
package com.laawe.purchasing.auth.model.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record AllUsersResponse(
        UUID idf,
        String fullName,
        String email,
        String phoneNumber,
        String roleName,
        String employeeId,
        String username,
        String status,
        Boolean isAdmin,
        LocalDateTime createdAt
) {
}

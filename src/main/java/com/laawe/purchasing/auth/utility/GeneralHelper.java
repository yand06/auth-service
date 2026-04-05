package com.laawe.purchasing.auth.utility;

import com.laawe.purchasing.auth.repository.UserRepository;

import java.util.UUID;

public final class GeneralHelper {

    public static final String XXX = "XXX";
    public static final String ACTIVE = "ACTIVE";
    public static final String INACTIVE = "INACTIVE";
    public static final String DELETED = "DELETED";
    public static final String ANOMALI = "ANOMALI";

    public static String extractCode(String name) {
        if (name == null) return XXX;
        int length = Math.min(name.length(), 3);
        return name.substring(0, length).toUpperCase();
    }

    public static String extractUserStatus(Integer userStatus) {
        if (userStatus == 0) return ANOMALI;
        return switch (userStatus) {
            case 1 -> ACTIVE;
            case 2 -> INACTIVE;
            case 3 -> DELETED;
            default -> ANOMALI;
        };
    }

    public static Boolean isUserAdmin(String userIdf, UserRepository userRepository) {
        return userRepository.isUserAdmin(UUID.fromString(userIdf));
    }

    public static String generateEmployeeId(String departmentName, String roleName, UserRepository userRepository) {
        String deptCode = extractCode(departmentName);
        String roleCode = extractCode(roleName);

        Long seq = userRepository.getNextEmployeeSequence();

        return String.format("%s-%s-%03d", deptCode, roleCode, seq);
    }
}

package com.laawe.purchasing.auth.model.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRegisterRequest {
    @NotBlank(message = "Username is required")
    @Size(min = 8, max = 25, message = "Username must be between 8 and 25 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username must contain only letters, numbers, and underscores")
    private String userUsername;

    @NotBlank(message = "Phone number is required")
    private String userPhoneNumber;

    @NotBlank(message = "Email is required")
    @Email(message = "Email is invalid")
    private String userEmail;

    @NotBlank(message = "Full is required")
    private String userFullName;

    @NotBlank(message = "Role is required")
    private String userRole;

    @NotNull(message = "Is Active is required")
    private Boolean userIsActive;

    @NotNull(message = "Is Admin is required")
    private Boolean userIsAdmin;
}

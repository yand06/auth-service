package com.laawe.purchasing.auth.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Username/Email/Phone Number tidak boleh kosong")
    private String identifier;

    @NotBlank(message = "Password tidak boleh kosong")
    private String password;
}

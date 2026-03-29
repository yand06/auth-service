package com.laawe.purchasing.auth.service;

import com.laawe.purchasing.auth.model.request.LoginRequest;
import com.laawe.purchasing.auth.model.request.LogoutRequest;
import com.laawe.purchasing.auth.model.response.GenericApiResponse;
import com.laawe.purchasing.auth.model.response.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    GenericApiResponse<LoginResponse> login(LoginRequest loginRequest);
    GenericApiResponse<?> logout(LogoutRequest logoutRequest, HttpServletRequest httpServletRequest);
}

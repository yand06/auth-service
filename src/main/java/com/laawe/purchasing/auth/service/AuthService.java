package com.laawe.purchasing.auth.service;

import com.laawe.purchasing.auth.model.request.LoginRequest;
import com.laawe.purchasing.auth.model.request.LogoutRequest;
import com.laawe.purchasing.auth.model.response.GenericApiResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
    GenericApiResponse<?> getLogin(LoginRequest loginRequest);
    GenericApiResponse<?> getLogout(LogoutRequest logoutRequest, HttpServletRequest httpServletRequest);
}

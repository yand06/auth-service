package com.laawe.purchasing.auth.service;

import com.laawe.purchasing.auth.model.request.ChangePasswordRequest;
import com.laawe.purchasing.auth.model.request.UserRegisterRequest;
import com.laawe.purchasing.auth.model.response.GenericApiResponse;

public interface AdminUserService {
    GenericApiResponse<?> getProfile(String loggedInUserIdf);
    GenericApiResponse<?> getRegister(UserRegisterRequest userRegisterRequest, String loggedInUserIdf);
    GenericApiResponse<?> changePassword(ChangePasswordRequest changePasswordRequest, String loggedInUserIdf);
}

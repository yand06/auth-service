package com.laawe.purchasing.auth.service;

import com.laawe.purchasing.auth.model.request.UserRegisterRequest;
import com.laawe.purchasing.auth.model.response.GenericApiResponse;

public interface AdminUserService {
    GenericApiResponse<?> getProfile(String loggedInUserIdf);
    GenericApiResponse<?> getRegister(UserRegisterRequest userRegisterRequest);
}

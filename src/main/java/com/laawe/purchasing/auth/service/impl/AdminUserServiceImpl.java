package com.laawe.purchasing.auth.service.impl;

import com.laawe.purchasing.auth.config.constant.ResponseCode;
import com.laawe.purchasing.auth.controller.handler.BusinessException;
import com.laawe.purchasing.auth.model.entity.M_User;
import com.laawe.purchasing.auth.model.response.GenericApiResponse;
import com.laawe.purchasing.auth.model.response.ProfileResponse;
import com.laawe.purchasing.auth.repository.UserRepository;
import com.laawe.purchasing.auth.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final UserRepository userRepository;

    @Override
    public GenericApiResponse<ProfileResponse> getProfile(String loggedInUserIdf) {

        M_User user = userRepository.findByIdf(UUID.fromString(loggedInUserIdf))
                .orElseThrow(() -> new BusinessException(ResponseCode.USER_NOT_FOUND, "USER NOT FOUND"));

        return GenericApiResponse.success(new ProfileResponse()
                .setUserIdf(user.getIdf())
                .setUserFullName(user.getFullName())
                .setUserRoleName(user.getRole().getName())
                .setUserName(user.getUsername())
                , "SUCCESSFULLY GET PROFILE DATA");
    }

}

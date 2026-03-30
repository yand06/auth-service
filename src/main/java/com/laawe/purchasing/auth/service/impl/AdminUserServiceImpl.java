package com.laawe.purchasing.auth.service.impl;

import com.laawe.purchasing.auth.config.SecurityConfig;
import com.laawe.purchasing.auth.config.constant.ResponseCode;
import com.laawe.purchasing.auth.config.i18n.Translator;
import com.laawe.purchasing.auth.controller.handler.BusinessException;
import com.laawe.purchasing.auth.model.entity.M_Role;
import com.laawe.purchasing.auth.model.entity.M_User;
import com.laawe.purchasing.auth.model.request.UserRegisterRequest;
import com.laawe.purchasing.auth.model.response.GenericApiResponse;
import com.laawe.purchasing.auth.model.response.ProfileResponse;
import com.laawe.purchasing.auth.repository.RoleRepository;
import com.laawe.purchasing.auth.repository.UserRepository;
import com.laawe.purchasing.auth.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.laawe.purchasing.auth.config.constant.AppConstant.DEFAULT_PASSWORD;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final SecurityConfig securityConfig;

    @Override
    public GenericApiResponse<ProfileResponse> getProfile(String loggedInUserIdf) {

        M_User user = userRepository.findByIdf(UUID.fromString(loggedInUserIdf))
                .orElseThrow(
                        () -> new BusinessException(
                                ResponseCode.USER_NOT_FOUND,
                                Translator.toLocale(ResponseCode.USER_NOT_FOUND.getMessageKey())
                        )
                );

        return GenericApiResponse.success(new ProfileResponse()
                .setUserIdf(user.getIdf())
                .setUserFullName(user.getFullName())
                .setUserRoleName(user.getRole().getName())
                .setUserName(user.getUsername())
                , "SUCCESSFULLY GET PROFILE DATA");
    }

    @Override
    public GenericApiResponse<?> getRegister(UserRegisterRequest userRegisterRequest) {
        if(userRepository.existsByUsername(userRegisterRequest.getUserUsername())){
            throw new BusinessException(ResponseCode.USERNAME_EXISTS, Translator.toLocale(ResponseCode.USERNAME_EXISTS.getMessageKey()));
        }

        if (userRepository.existsByEmail(userRegisterRequest.getUserEmail())){
            throw new BusinessException(ResponseCode.EMAIL_EXISTS, Translator.toLocale(ResponseCode.EMAIL_EXISTS.getMessageKey()));
        }

        if(userRepository.existsByPhoneNumber(userRegisterRequest.getUserPhoneNumber())){
            throw new BusinessException(ResponseCode.PHONE_NUMBER_EXISTS, Translator.toLocale(ResponseCode.PHONE_NUMBER_EXISTS.getMessageKey()));
        }

        M_Role role = roleRepository.findByName(userRegisterRequest.getUserRole())
                .orElseThrow(() -> new BusinessException(ResponseCode.ROLE_NOT_FOUND, Translator.toLocale(ResponseCode.ROLE_NOT_FOUND.getMessageKey())));

        String hasPassword = securityConfig.passwordEncoder().encode(DEFAULT_PASSWORD);

        M_User newUser = M_User.builder()
                .idf(UUID.randomUUID())
                .username(userRegisterRequest.getUserUsername())
                .email(userRegisterRequest.getUserEmail())
                .password(hasPassword)
                .fullName(userRegisterRequest.getUserFullName())
                .role(role)
                .isActive(userRegisterRequest.getUserIsActive())
                .isAdmin(userRegisterRequest.getUserIsAdmin())
                .phoneNumber(userRegisterRequest.getUserPhoneNumber())
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(newUser);

        return GenericApiResponse.success(null, "SUCCESSFULLY REGISTER USER");
    }

}

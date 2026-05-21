package com.laawe.purchasing.auth.service.impl;

import com.laawe.purchasing.auth.config.SecurityConfig;
import com.laawe.purchasing.auth.config.constant.ResponseCode;
import com.laawe.purchasing.auth.config.i18n.Translator;
import com.laawe.purchasing.auth.controller.handler.BusinessException;
import com.laawe.purchasing.auth.model.entity.M_Role;
import com.laawe.purchasing.auth.model.entity.M_User;
import com.laawe.purchasing.auth.model.entity.M_User_Detail;
import com.laawe.purchasing.auth.model.request.ChangePasswordRequest;
import com.laawe.purchasing.auth.model.request.UserRegisterRequest;
import com.laawe.purchasing.auth.model.response.AllUsersDTO;
import com.laawe.purchasing.auth.model.response.GenericApiResponse;
import com.laawe.purchasing.auth.model.response.ProfileResponse;
import com.laawe.purchasing.auth.repository.RoleRepository;
import com.laawe.purchasing.auth.repository.UserDetailRepository;
import com.laawe.purchasing.auth.repository.UserRepository;
import com.laawe.purchasing.auth.service.AdminUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.laawe.purchasing.auth.config.constant.AppConstant.DEFAULT_PASSWORD;
import static com.laawe.purchasing.auth.config.constant.AppConstant.SUPERUSER_ROLE_NAME;
import static com.laawe.purchasing.auth.utility.GeneralHelper.*;
import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@Service
@RequiredArgsConstructor
@Slf4j
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class AdminUserServiceImpl implements AdminUserService {

    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository;
    private final RoleRepository roleRepository;
    private final SecurityConfig securityConfig;

    @Override
    public GenericApiResponse<ProfileResponse> getProfile(String loggedInUserIdf) {

        M_User user = userRepository.findByIdf(UUID.fromString(loggedInUserIdf))
                .orElseThrow(() -> new BusinessException(
                        ResponseCode.USER_NOT_FOUND,
                        Translator.toLocale(ResponseCode.USER_NOT_FOUND.getMessageKey()))
                );

        Optional<M_User_Detail> userDetail = Optional.of(userDetailRepository.findByUser(user)
                .orElseThrow(() -> new BusinessException(
                        ResponseCode.USER_NOT_FOUND, Translator.toLocale(ResponseCode.USER_NOT_FOUND.getMessageKey())))
        );

        return GenericApiResponse.success(new ProfileResponse()
                .setUserIdf(user.getIdf())
                .setUserFullName(user.getFullName())
                .setUserEmail(user.getEmail())
                .setUserRoleName(user.getRole().getName())
                .setUserEmployeeId(user.getEmployeeId())
                .setUserName(user.getUsername()).setUserPhoneNumber(user.getPhoneNumber())
                .setUserStatus(extractUserStatus(user.getStatus()))
                .setUserIsAdmin(user.getIsAdmin())
                .setUserDepartmentName(userDetail.map(M_User_Detail::getUserDetailDepartmentName).orElse(null))
                .setUserAvatar(userDetail.map(M_User_Detail::getUserAvatar).orElse(null))
                .setUserJoinDate(user.getCreatedAt())
                .setUserOfficeLocation(userDetail.map(M_User_Detail::getUserOfficeLocation).orElse(null))
                , "SUCCESSFULLY GET PROFILE DATA");
    }

    @Override
    @Transactional
    public GenericApiResponse<?> getRegister(UserRegisterRequest userRegisterRequest, String loggedInUserIdf) {

        if (!isUserAdmin(loggedInUserIdf, userRepository)){
            throw new BusinessException(ResponseCode.FORBIDDEN, Translator.toLocale(ResponseCode.FORBIDDEN.getMessageKey()));
        }

        if(userRepository.existsByUsername(userRegisterRequest.getUserUsername())){
            throw new BusinessException(ResponseCode.USERNAME_EXISTS, Translator.toLocale(ResponseCode.USERNAME_EXISTS.getMessageKey()));
        }

        if (userRepository.existsByEmail(userRegisterRequest.getUserEmail())){
            throw new BusinessException(ResponseCode.EMAIL_EXISTS, Translator.toLocale(ResponseCode.EMAIL_EXISTS.getMessageKey()));
        }

        if (userRepository.existsByPhoneNumber(userRegisterRequest.getUserPhoneNumber())){
            throw new BusinessException(ResponseCode.PHONE_NUMBER_EXISTS, Translator.toLocale(ResponseCode.PHONE_NUMBER_EXISTS.getMessageKey()));
        }

        M_Role role = roleRepository.findByName(userRegisterRequest.getUserRole().toUpperCase())
                .orElseThrow(() -> new BusinessException(ResponseCode.ROLE_NOT_FOUND, Translator.toLocale(ResponseCode.ROLE_NOT_FOUND.getMessageKey())));

        String hasPassword = securityConfig.passwordEncoder().encode(DEFAULT_PASSWORD);

        String employeeID = generateEmployeeId(userRegisterRequest.getUserDepartmentName(), userRegisterRequest.getUserRole().toUpperCase(), userRepository);

        M_User newUser = M_User.builder()
                .idf(UUID.randomUUID())
                .username(userRegisterRequest.getUserUsername())
                .email(userRegisterRequest.getUserEmail())
                .password(hasPassword)
                .fullName(userRegisterRequest.getUserFullName())
                .role(role)
                .status(userRegisterRequest.getUserStatus())
                .isAdmin(userRegisterRequest.getUserIsAdmin())
                .phoneNumber(userRegisterRequest.getUserPhoneNumber())
                .createdAt(LocalDateTime.now())
                .employeeId(employeeID)
                .build();

        M_User_Detail newUserDetail = M_User_Detail.builder()
                .userDetailIdf(UUID.randomUUID())
                .user(newUser)
                .userAvatar(userRegisterRequest.getUserAvatar())
                .userOfficeLocation(userRegisterRequest.getUserOfficeLocation())
                .userDetailDepartmentName(userRegisterRequest.getUserDepartmentName())
                .userDetailCreatedAt(LocalDateTime.now())
                .build();

        userRepository.save(newUser);
        userDetailRepository.save(newUserDetail);

        return GenericApiResponse.success(null, "SUCCESSFULLY REGISTER USER");
    }

    @Override
    @Transactional
    public GenericApiResponse<?> changePassword(ChangePasswordRequest changePasswordRequest, String loggedInUserIdf) {

        M_User user = userRepository.findByIdf(UUID.fromString(loggedInUserIdf))
                .orElseThrow(() -> new BusinessException(
                        ResponseCode.USER_NOT_FOUND,
                        Translator.toLocale(ResponseCode.USER_NOT_FOUND.getMessageKey()))
                );

        if (!securityConfig.passwordEncoder().matches(changePasswordRequest.getCurrentPassword(), user.getPassword())){
            throw new BusinessException(
                    ResponseCode.INVALID_PASSWORD,
                    Translator.toLocale(ResponseCode.INVALID_PASSWORD.getMessageKey())
            );
        }

        if (securityConfig.passwordEncoder().matches(changePasswordRequest.getNewPassword(), user.getPassword())){
            throw new BusinessException(
                    ResponseCode.PASSWORD_MUST_BE_DIFFERENT,
                    Translator.toLocale(ResponseCode.PASSWORD_MUST_BE_DIFFERENT.getMessageKey())
            );
        }

        String newPassword = securityConfig.passwordEncoder().encode(changePasswordRequest.getNewPassword());
        user.setPassword(newPassword);
        userRepository.save(user);

        return GenericApiResponse.success(null, "SUCCESSFULLY CHANGE PASSWORD");
    }

    @Override
    public GenericApiResponse<?> getAllUsers(String loggedInUserIdf, int page, int size) {
        if (!isSuperUser(UUID.fromString(loggedInUserIdf), userRepository).equals(SUPERUSER_ROLE_NAME)){
            throw new BusinessException(ResponseCode.FORBIDDEN, Translator.toLocale(ResponseCode.FORBIDDEN.getMessageKey()));
        }

        if (page < 1) page = 1;
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt").descending());

        Page<M_User> usersData = userRepository.findAllIsSuperUsers(pageable);

        Page<AllUsersDTO> data = usersData.map(user -> new AllUsersDTO(
                user.getIdf(),
                user.getFullName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole().getName(),
                user.getEmployeeId(),
                user.getUsername(),
                extractUserStatus(user.getStatus()),
                user.getIsAdmin(),
                user.getCreatedAt()
        ));

        return GenericApiResponse.success(data, "SUCCESSFULLY GET ALL USERS");
    }

}

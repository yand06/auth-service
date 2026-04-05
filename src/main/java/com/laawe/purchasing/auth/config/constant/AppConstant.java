package com.laawe.purchasing.auth.config.constant;

public class AppConstant {

    public static final String BASE_API_URL = "/api/v1/auth";

    public static final String LOGIN_API = "/user/login";
    public static final String LOGOUT_API = "/user/logout";
    public static final String PROFILE_API = "/user/me";
    public static final String REGISTER_API = "/user/register";
    public static final String REFRESH_TOKEN_API = "/user/refresh-token";

    public static final String PUBLIC = "public";

    /* M_USER TABLE */
    public static final String TABLE_USER = "m_user";
    public static final String USER_ID = "user_id";
    public static final String USER_IDF = "user_idf";
    public static final String USER_USERNAME = "user_username";
    public static final String USER_PHONE_NUMBER = "user_phone_number";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_PASSWORD_HASH = "user_password_hash";
    public static final String USER_FULL_NAME = "user_full_name";
    public static final String USER_ROLE_ID = "user_role_id";
    public static final String USER_STATUS = "user_status";
    public static final String USER_CREATED_AT = "user_created_at";
    public static final String USER_IS_ADMIN = "user_is_admin";
    public static final String USER_EMPLOYEE_ID = "user_employee_id";

    /* M_ROLE TABLE */
    public static final String TABLE_ROLE = "m_role";
    public static final String ROLE_ID = "role_id";
    public static final String ROLE_IDF = "role_idf";
    public static final String ROLE_NAME = "role_name";
    public static final String ROLE_DESCRIPTION = "role_description";

    /* M_USER_DETAIL */
    public static final String TABLE_USER_DETAIL = "m_user_detail";
    public static final String USER_DETAIL_ID = "user_detail_id";
    public static final String USER_DETAIL_IDF = "user_detail_idf";
    public static final String USER_AVATAR = "user_avatar";
    public static final String USER_OFFICE_LOCATION = "user_office_location";
    public static final String USER_DETAIL_CREATED_AT = "user_detail_created_at";
    public static final String USER_DETAIL_UPDATED_AT = "user_detail_updated_at";
    public static final String USER_DETAIL_DEPARTMENT_NAME = "user_detail_department_name";

    public static final String HEADER_BEARER = "Bearer ";
    public static final String HEADER_X_USER_ID = "X-User-ID";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String BL_PREFIX = "BLACKLIST:";
    public static final String EMPTY = "";
    public static final String SUCCESS_STATUS = "SUCCESS";
    public static final String ERROR_STATUS = "ERROR";
    public static final Long ONE_HOURS_IN_MILLISECONDS = 3600000L;
    public static final Long SEVEN_DAYS_IN_MILLISECONDS = 604800000L;
    public static final String TEXT = "TEXT";

    public static final String SUPERUSER_PASSWORD = "admin1234";
    public static final String DEFAULT_PASSWORD = "12345678";
}

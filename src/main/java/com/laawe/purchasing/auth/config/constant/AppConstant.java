package com.laawe.purchasing.auth.config.constant;

public class AppConstant {

    public static final String BASE_API_URL = "/api/v1/auth";
    public static final String LOGIN_API = "/user/login";
    public static final String LOGOUT_API = "/user/logout";

    public static final String PUBLIC = "public";

    public static final String TABLE_USER = "m_user";
    public static final String USER_ID = "user_id";
    public static final String USER_IDF = "user_idf";
    public static final String USER_USERNAME = "user_username";
    public static final String USER_EMAIL = "user_email";
    public static final String USER_PASSWORD_HASH = "user_password_hash";
    public static final String USER_FULL_NAME = "user_full_name";
    public static final String USER_ROLE_ID = "user_role_id";
    public static final String USER_IS_ACTIVE = "user_is_active";
    public static final String USER_CREATED_AT = "user_created_at";

    public static final String TABLE_ROLE = "m_role";
    public static final String ROLE_ID = "role_id";
    public static final String ROLE_IDF = "role_idf";
    public static final String ROLE_NAME = "role_name";
    public static final String ROLE_DESCRIPTION = "role_description";

    public static final String HEADER_BEARER = "Bearer ";
    public static final String HEADER_AUTHORIZATION = "Authorization ";
    public static final String BL_PREFIX = "BLACKLIST:";
    public static final String EMPTY = "";
}

package com.laawe.purchasing.auth.config.constant;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseCode {

    // --- SUCCESS CODES ---
    SUCCESS(HttpStatus.OK, "00", "response.code.success"),
    CREATED(HttpStatus.CREATED, "01", "response.code.created"),

    // --- CLIENT ERRORS ---
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "02", "response.code.bad_request"),
    INVALID_IDENTIFIER(HttpStatus.UNAUTHORIZED, "02", "response.code.invalid_identifier"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "04", "response.code.user_not_found"),
    ROLE_NOT_FOUND(HttpStatus.NOT_FOUND, "04", "response.code.role_not_found"),
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "05", "response.code.user_already_exists"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "06", "response.code.forbidden"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "07","response.code.unauthorized"),
    USER_NOT_ACTIVE(HttpStatus.BAD_REQUEST, "02","response.code.user_not_active"),
    USER_DELETED(HttpStatus.BAD_REQUEST, "02","response.code.user_deleted"),
    USERNAME_EXISTS(HttpStatus.BAD_REQUEST, "02","response.code.user_name_exists"),
    EMAIL_EXISTS(HttpStatus.BAD_REQUEST, "02","response.code.email_exists"),
    PHONE_NUMBER_EXISTS(HttpStatus.BAD_REQUEST, "02","response.code.phone_number_exists"),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "02","response.code.invalid_password"),
    PASSWORD_MUST_BE_DIFFERENT(HttpStatus.BAD_REQUEST, "02","response.code.password_must_be_different"),

    // --- SERVER ERRORS ---
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "99", "response.code.internal_server_error");

    private final HttpStatus httpStatus;
    private final String code;

    private final String messageKey;

    ResponseCode(HttpStatus httpStatus, String code, String messageKey) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.messageKey = messageKey;
    }
}
package com.laawe.purchasing.auth.controller.handler;

import com.laawe.purchasing.auth.config.constant.ResponseCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final ResponseCode responseCode;

    public BusinessException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
    }

    public BusinessException(ResponseCode responseCode, String customMessage) {
        super(customMessage);
        this.responseCode = responseCode;
    }
}
package com.laawe.purchasing.auth.controller.handler;

import com.laawe.purchasing.auth.config.constant.ResponseCode;
import com.laawe.purchasing.auth.config.i18n.Translator;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final ResponseCode responseCode;

    public BusinessException(ResponseCode responseCode) {
        super(Translator.toLocale(responseCode.getMessageKey()));
        this.responseCode = responseCode;
    }

    public BusinessException(ResponseCode responseCode, String customMessage) {
        super(customMessage);
        this.responseCode = responseCode;
    }
}
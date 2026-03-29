package com.laawe.purchasing.auth.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.laawe.purchasing.auth.config.constant.ResponseCode;
import lombok.Data;
import lombok.experimental.Accessors;

import static com.laawe.purchasing.auth.config.constant.AppConstant.ERROR_STATUS;
import static com.laawe.purchasing.auth.config.constant.AppConstant.SUCCESS_STATUS;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericApiResponse<T> {
    private String status;
    private String code;
    private String message;
    private T data;

    // 1. Success standar
    public static <T> GenericApiResponse<T> success(T data) {
        // Tambahkan <T> pada tipe kembalian dan new GenericApiResponse<T>()
        return new GenericApiResponse<T>()
                .setStatus(SUCCESS_STATUS)
                .setCode(ResponseCode.SUCCESS.getCode())
                .setMessage(ResponseCode.SUCCESS.getMessage())
                .setData(data);
    }

    // 2. Success dengan pesan kustom
    public static <T> GenericApiResponse<T> success(T data, String customMessage) {
        return new GenericApiResponse<T>()
                .setStatus(SUCCESS_STATUS)
                .setCode(ResponseCode.SUCCESS.getCode())
                .setMessage(customMessage)
                .setData(data);
    }

    // 3. Error standar (tanpa data)
    public static <T> GenericApiResponse<T> error(ResponseCode responseCode) {
        return new GenericApiResponse<T>()
                .setStatus(ERROR_STATUS)
                .setCode(responseCode.getCode())
                .setMessage(responseCode.getMessage())
                .setData(null);
    }

    // 4. Error dengan data (Sangat berguna untuk error validasi @NotBlank)
    public static <T> GenericApiResponse<T> error(ResponseCode responseCode, T data) {
        return new GenericApiResponse<T>()
                .setStatus(ERROR_STATUS)
                .setCode(responseCode.getCode())
                .setMessage(responseCode.getMessage())
                .setData(data);
    }
}
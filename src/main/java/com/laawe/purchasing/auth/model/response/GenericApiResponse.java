package com.laawe.purchasing.auth.model.response;
import com.laawe.purchasing.auth.config.constant.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenericApiResponse<T> {
    private String status;
    private String code;
    private String message;
    private T data;

    // 1. Success standar (menggunakan pesan bawaan dari Enum)
    public static <T> GenericApiResponse<T> success(T data) {
        return GenericApiResponse.<T>builder()
                .status("SUCCESS")
                .code(ResponseCode.SUCCESS.getCode())
                .message(ResponseCode.SUCCESS.getMessage())
                .data(data)
                .build();
    }

    // 2. Success dengan pesan kustom (Contoh: "Login berhasil, selamat datang!")
    public static <T> GenericApiResponse<T> success(T data, String customMessage) {
        return GenericApiResponse.<T>builder()
                .status("SUCCESS")
                .code(ResponseCode.SUCCESS.getCode())
                .message(customMessage)
                .data(data)
                .build();
    }

    // 3. Error standar (tanpa data)
    public static <T> GenericApiResponse<T> error(ResponseCode responseCode) {
        return GenericApiResponse.<T>builder()
                .status("ERROR")
                .code(responseCode.getCode())
                .message(responseCode.getMessage())
                .data(null)
                .build();
    }

    // 4. Error dengan data (Sangat berguna untuk error validasi @NotBlank)
    public static <T> GenericApiResponse<T> error(ResponseCode responseCode, T data) {
        return GenericApiResponse.<T>builder()
                .status("ERROR")
                .code(responseCode.getCode())
                .message(responseCode.getMessage())
                .data(data)
                .build();
    }

}

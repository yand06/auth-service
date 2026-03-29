package com.laawe.purchasing.auth.controller.handler;

import com.laawe.purchasing.auth.config.constant.ResponseCode;
import com.laawe.purchasing.auth.model.response.GenericApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Menangkap Error Validasi Field (seperti @NotBlank, @Email)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GenericApiResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        // Looping semua field yang error dan ambil pesan error-nya
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        // 🔥 Langsung gunakan helper GenericApiResponse.error() dengan membawa data map 'errors'
        return ResponseEntity
                .status(ResponseCode.BAD_REQUEST.getHttpStatus())
                .body(GenericApiResponse.error(ResponseCode.BAD_REQUEST, errors));
    }

    // 2. Menangkap Error Business Logic (Contoh: User tidak ada, Password salah)
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<GenericApiResponse<Object>> handleBusinessExceptions(BusinessException ex) {

        // Ambil ResponseCode dari exception yang dilempar oleh Service
        ResponseCode responseCode = ex.getResponseCode();

        // 🔥 Langsung gunakan helper GenericApiResponse.error() tanpa data tambahan
        return ResponseEntity
                .status(responseCode.getHttpStatus())
                .body(GenericApiResponse.error(responseCode));
    }

    // 3. Menangkap semua Error Server (500) yang tidak terduga
    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericApiResponse<Object>> handleAllOtherExceptions(Exception ex) {
        // Cetak log di terminal agar developer tahu penyebab aslinya
        ex.printStackTrace();

        return ResponseEntity
                .status(ResponseCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(GenericApiResponse.error(ResponseCode.INTERNAL_SERVER_ERROR));
    }
}
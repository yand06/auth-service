package com.laawe.purchasing.auth.config.constant;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseCode {

    // --- SUCCESS CODES (Biasanya dimulai dengan 0) ---
    SUCCESS(HttpStatus.OK, "00", "Operasi berhasil"),
    CREATED(HttpStatus.CREATED, "01", "Data berhasil dibuat"),

    // --- CLIENT ERRORS (Biasanya dimulai dengan angka lain) ---
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "02", "Permintaan tidak valid atau data tidak lengkap"),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "03", "Username atau password salah!"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "04", "User tidak ditemukan!"),
    USER_ALREADY_EXISTS(HttpStatus.CONFLICT, "05", "Username atau email sudah terdaftar!"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "06", "Akses ditolak, role tidak sesuai!"),

    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "07","Sesi telah berakhir"),

    // --- SERVER ERRORS (Biasanya 99 untuk error sistem yang tidak terduga) ---
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "99", "Terjadi kesalahan internal pada server");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ResponseCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
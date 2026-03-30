package com.laawe.purchasing.auth.utility;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public final class TimeHelper {

    private static final ZoneId DEFAULT_ZONE = ZoneId.systemDefault();
    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private TimeHelper() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * 1. Konversi milidetik ke Instant (Standard Emas untuk Waktu Mutlak)
     * Sangat disarankan untuk tipe data Entity JPA / Database (menggantikan java.sql.Timestamp)
     */
    public static Instant toInstant(Long milliseconds) {
        return Instant.ofEpochMilli(milliseconds);
    }

    /**
     * 2. Konversi milidetik ke LocalDateTime
     * Berguna jika kamu ingin melakukan kalkulasi tambahan (misal: tambah hari, cek jam kerja)
     */
    public static LocalDateTime toLocalDateTime(Long milliseconds) {
        return Instant.ofEpochMilli(milliseconds)
                .atZone(DEFAULT_ZONE)
                .toLocalDateTime();
    }

    /**
     * 3. Konversi milidetik ke String
     * Berguna untuk dikembalikan ke Frontend via JSON atau untuk dicetak di Terminal (Log)
     */
    public static String toFormattedString(Long milliseconds) {
        return toLocalDateTime(milliseconds).format(DEFAULT_FORMATTER);
    }
}

package com.empathday.empathdayapi.common.utils;

public class NumberUtils {

    public static boolean isNullOrZero(Long data) {
        return data == null || data == 0L;
    }

    public static boolean isNullOrZero(Integer data) {
        return data == null || data == 0;
    }

    public static boolean isNotNullOrZero(Long data) {
        return !isNullOrZero(data);
    }

    public static boolean isNotNullOrZero(Integer data) {
        return !isNullOrZero(data);
    }
}

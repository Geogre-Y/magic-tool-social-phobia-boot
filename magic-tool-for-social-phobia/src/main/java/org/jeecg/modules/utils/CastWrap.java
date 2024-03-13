package org.jeecg.modules.utils;

/**
 * @author 余吉钊
 * @date 2024/3/7 15:23
 */
public class CastWrap {
    public static final String CAST_ERROR_CODE = "CAST_ERROR";

    public CastWrap() {
    }

    public static <T> T cast(Object obj) throws RuntimeException {
        try {
            return (T) obj;
        } catch (ClassCastException var2) {
            throw new RuntimeException("CAST_ERROR");
        }
    }
}

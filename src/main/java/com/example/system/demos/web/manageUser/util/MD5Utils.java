package com.example.system.demos.web.manageUser.util;

import java.security.MessageDigest;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
public class MD5Utils {
    private static final String SECRET_KEY = "sdsggsgfnsdk213";

    public static String getSECRET_KEY() {
        return SECRET_KEY;
    }
    private static String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    // 生成一个简单的 Token（这里用 MD5 作为示例，实际可使用更安全的 HMAC）
    public static String generateWay(String str) {
        return md5(str + MD5Utils.getSECRET_KEY());
    }

}


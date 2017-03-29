package com.test.bi.session;

import android.text.TextUtils;

public class SessionUtil {

    /**
     * 创建一个新的Session
     */
    public static String createNewSession(String cid, String userId) {
        StringBuffer sb = new StringBuffer();
        // 设备Id
        if (TextUtils.isEmpty(cid) == false) {
            sb.append(cid);
        }
        // 用户Id
        if (TextUtils.isEmpty(userId) == false) {
            sb.append(userId);
        }
        // 时间戳
        sb.append(System.currentTimeMillis());
        // md5
        String sig = md5(sb.toString()).toLowerCase();
        return sig;
    }

    private static String md5(String s) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            StringBuilder result = new StringBuilder();
            byte[] bs = md.digest(s.getBytes("UTF-8"));
            for (int i = 0; i < bs.length; i++) {
                byte b = bs[i];
                result.append(Integer.toHexString((b & 0xf0) >>> 4));
                result.append(Integer.toHexString(b & 0x0f));
            }
            return result.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }
}

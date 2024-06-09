package com.pandaer.server.utils;

/**
 * 获取登录的用户ID
 */
public class LoginIDUtil {
    private static final ThreadLocal<String> localUserId = new ThreadLocal<>();

    public static String getLoginID() {
        return localUserId.get();
    }

    public static void setLoginID(String id) {
        localUserId.set(id);
    }
}

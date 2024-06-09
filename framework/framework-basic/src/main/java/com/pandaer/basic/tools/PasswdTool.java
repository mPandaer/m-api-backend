package com.pandaer.basic.tools;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import com.pandaer.basic.exception.FrameworkException;
import com.pandaer.basic.resp.DefaultRespCode;


import java.nio.charset.StandardCharsets;

/**
 * 生成Hash密码，通过原始密码验证密码
 */
public class PasswdTool {
    /**
     * 动态盐值的位数
     */
    private static final Integer saltLen = 6;

    private static final String DIGEST_PASSWORD_TEMPLATE = "%s$%s";

    /**
     * 加密秘钥 随机的32为字符
     */
    private static final String key = "eOf2Seym0E1ZHO8ip2jqMta3IFtkaGfX";
    /**
     * 根据原密码生成Hash密码
     * @param rawPasswd
     * @return
     */
    public static String genPasswd(String rawPasswd) {
        String salt = RandomUtil.randomString(saltLen);
        return createPasswd(rawPasswd,salt);
    }

    public static boolean validPasswd(String rawPasswd,String cryptoPasswd) {
        if (cryptoPasswd == null || !cryptoPasswd.contains("$")) {
            throw new FrameworkException(DefaultRespCode.FRAMEWORK_ERROR);
        }
        String salt = cryptoPasswd.split("\\$")[0];
        String newPasswd = createPasswd(rawPasswd, salt);
        return newPasswd.equals(cryptoPasswd);
    }

    private static String createPasswd(String rawPasswd,String salt) {
        HMac hmac = DigestUtil.hmac(HmacAlgorithm.HmacSHA256, key.getBytes(StandardCharsets.UTF_8));
        String encPassword = String.format(DIGEST_PASSWORD_TEMPLATE, salt, rawPasswd);
        byte[] digest = hmac.digest(encPassword);
        String b64Passwd = Base64.encode(digest);
        return String.format(DIGEST_PASSWORD_TEMPLATE,salt,b64Passwd);
    }

    public static void main(String[] args) {
        String rawPasswd = "12345678";
        String cryptoPasswd = genPasswd(rawPasswd);
        System.out.println(cryptoPasswd);
        System.out.println(validPasswd(rawPasswd,cryptoPasswd));
    }
}

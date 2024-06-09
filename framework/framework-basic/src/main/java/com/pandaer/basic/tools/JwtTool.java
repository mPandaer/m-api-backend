package com.pandaer.basic.tools;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import lombok.Data;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtTool {
    private static final String EXP_KEY = "exp";
    private static final Integer TOKEN_EXP_TIME = 25 * 60 * 1000;
    private static final String key = "eOf2Seym0E1ZHO8ip2jqMta3IFtkaGfX";
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";


    public static String genToken(Map<String,String> payload) {
        return createToken(payload,TOKEN_EXP_TIME);
    }

    public static boolean validToken(String token) {
        boolean res = JWTUtil.verify(token, key.getBytes(StandardCharsets.UTF_8));
        if (!res) return false;
        JWT jwt = JWTUtil.parseToken(token);
        String date = (String) jwt.getPayload().getClaim(EXP_KEY);
        return DateUtil.date().isBefore(DateUtil.parse(date,DATE_TIME_FORMAT));
    }



    private static String createToken(Map<String, String> payload, Integer accessTokenExpTime) {
        Map<String, Object> map = payload.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
        map.put(EXP_KEY, DateUtil.date().offset(DateField.MILLISECOND, accessTokenExpTime).toString(DATE_TIME_FORMAT));
        return JWTUtil.createToken(map, key.getBytes(StandardCharsets.UTF_8));
    }

}


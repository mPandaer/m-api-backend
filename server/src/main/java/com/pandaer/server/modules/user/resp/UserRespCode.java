package com.pandaer.server.modules.user.resp;

import com.pandaer.basic.resp.IResponseCode;

public enum UserRespCode implements IResponseCode {
    PARAMS_ERROR(2400,"参数异常"),
    USER_NOT_EXIST(2601,"用户不存在"),
    USER_PASSWORD_ERROR(2602,"用户密码不正确"),
    USER_HAS_EXISTED(2603,"用户已经存在"),
    REGISTER_FAIL(2603,"注册失败"),
    ;

    private final Integer code;
    private final String message;

    UserRespCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

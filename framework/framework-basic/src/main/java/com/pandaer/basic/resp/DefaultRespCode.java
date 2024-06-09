package com.pandaer.basic.resp;

/**
 * 默认的业务状态相应码
 */
public enum DefaultRespCode implements IResponseCode {


    SUCCESS(1200,"success"),
    ERROR(1500,"error"),
    BUSINESS_ERROR(1510,"业务异常"),
    FRAMEWORK_ERROR(1510,"框架异常")
    ;

    private final Integer code;
    private final String message;

    DefaultRespCode(Integer code, String message) {
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

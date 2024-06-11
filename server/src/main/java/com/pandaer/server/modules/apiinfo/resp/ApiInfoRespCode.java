package com.pandaer.server.modules.apiinfo.resp;

import com.pandaer.basic.resp.IResponseCode;

public enum ApiInfoRespCode implements IResponseCode {
    ADD_API_INFO_PARAMS_EMPTY(3400,"添加接口信息参数异常"),
    ADD_API_INFO_FAIL(3500,"添加接口信息失败"),
    VALID_API_REQ_HEADER_FORMAT_FAIL(3800,"校验接口信息请求头格式失败"),
    VALID_API_REQ_PARAMS_FORMAT_FAIL(3801,"校验接口信息请求参数格式失败"),
    VALID_API_RESP_DESC_FORMAT_FAIL(3802,"校验接口信息响应体说明格式失败")
    ;

    private final Integer code;
    private final String message;

    ApiInfoRespCode(Integer code, String message) {
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

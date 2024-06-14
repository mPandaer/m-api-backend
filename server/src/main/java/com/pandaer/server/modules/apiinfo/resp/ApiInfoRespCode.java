package com.pandaer.server.modules.apiinfo.resp;

import com.pandaer.basic.resp.IResponseCode;

public enum ApiInfoRespCode implements IResponseCode {

    //参数相关异常
    ADD_API_INFO_PARAMS_EMPTY(3400,"添加接口信息参数异常"),
    UPDATE_API_INFO_PARAMS_EMPTY(3401,"更新接口信息参数异常"),
    PAGE_QUERY_API_INFO_PARAMS_EMPTY(3402,"分页查询接口信息参数异常"),
    API_INFO_ID_EMPTY(3403,"接口Id异常"),

    // 操作失败相关异常
    ADD_API_INFO_FAIL(3500,"添加接口信息失败"),
    UPDATE_API_INFO_FAIL(3501,"更新接口信息失败"),
    DELETE_API_INFO_FAIL(3502,"删除接口信息失败"),
    ONLINE_API_INFO_FAIL(3503,"上线接口异常"),
    OFFLINE_API_INFO_FAIL(3503,"下线接口异常"),


    API_INFO_NOT_EXIST(3700,"接口信息不存在"),


    //校验相关异常
    VALID_API_REQ_HEADER_FORMAT_FAIL(3800,"校验接口信息请求头格式失败"),
    VALID_API_REQ_PARAMS_FORMAT_FAIL(3801,"校验接口信息请求参数格式失败"),
    VALID_API_RESP_DESC_FORMAT_FAIL(3802,"校验接口信息响应体说明格式失败"),

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

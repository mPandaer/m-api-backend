package com.pandaer.web.resp;


import com.pandaer.basic.resp.IResponseCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static com.pandaer.basic.resp.DefaultRespCode.*;

/**
 * 公用响应对象
 */
@Getter
@Setter
@ToString
public class ComResp {
    private Integer code;
    private String message;
    private Object data;

    private ComResp(Integer code, String message, Object data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public static ComResp success() {
        return new ComResp(SUCCESS.getCode(), SUCCESS.getMessage(), null);
    }

    public static ComResp success(IResponseCode respCode, Object data) {
        return new ComResp(respCode.getCode(), respCode.getMessage(), data);
    }

    public static ComResp success(Object data) {
        return new ComResp(SUCCESS.getCode(), SUCCESS.getMessage(),data);
    }

    public static ComResp success(IResponseCode respCode) {
        return new ComResp(respCode.getCode(), respCode.getMessage(), null);
    }


    public static ComResp error() {
        return new ComResp(ERROR.getCode(), ERROR.getMessage(), null);
    }

    public static ComResp error(String message) {
        return new ComResp(ERROR.getCode(), message, null);
    }

    public static ComResp error(Integer code,String message) {
        return new ComResp(code, message, null);
    }


    public static ComResp error(IResponseCode respCode) {
        return new ComResp(respCode.getCode(), respCode.getMessage(), null);
    }

}

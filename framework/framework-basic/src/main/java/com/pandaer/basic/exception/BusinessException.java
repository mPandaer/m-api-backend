package com.pandaer.basic.exception;

import com.pandaer.basic.resp.IResponseCode;
import lombok.Data;
import lombok.Getter;

/**
 * 业务异常
 */

@Getter
public class BusinessException extends RuntimeException {
    private final Integer code;
    private final String message;

    public BusinessException(Integer code,String message) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public BusinessException(IResponseCode responseCode) {
        super(responseCode.getMessage());
        this.message = responseCode.getMessage();
        this.code = responseCode.getCode();
    }
}

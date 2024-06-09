package com.pandaer.basic.exception;

import com.pandaer.basic.resp.IResponseCode;
import lombok.Getter;

/**
 * 框架异常
 */

@Getter
public class FrameworkException extends RuntimeException {
    private final Integer code;
    private final String message;

    public FrameworkException(Integer code, String message) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public FrameworkException(IResponseCode responseCode) {
        super(responseCode.getMessage());
        this.message = responseCode.getMessage();
        this.code = responseCode.getCode();
    }
}

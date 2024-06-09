package com.pandaer.server.exception;

import com.pandaer.basic.resp.IResponseCode;
import lombok.Getter;

/**
 * 业务异常
 */

@Getter
public class AuthException extends RuntimeException {
    private final Integer code;
    private final String message;

    public AuthException(Integer code, String message) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public AuthException(IResponseCode responseCode) {
        super(responseCode.getMessage());
        this.message = responseCode.getMessage();
        this.code = responseCode.getCode();
    }
}

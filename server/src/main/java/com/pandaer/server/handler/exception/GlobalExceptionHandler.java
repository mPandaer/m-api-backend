package com.pandaer.server.handler.exception;

import cn.hutool.core.util.ObjUtil;
import com.pandaer.basic.exception.BusinessException;
import com.pandaer.basic.exception.FrameworkException;
import com.pandaer.server.exception.AuthException;
import com.pandaer.web.resp.ComResp;
import com.pandaer.basic.resp.DefaultRespCode;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * 全局异常处理器
 */
@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ComResp> handleBusinessException(BusinessException exception) {
        log.error("业务异常: ",exception);
        ComResp error = ComResp.error(exception.getCode(),exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(FrameworkException.class)
    public ResponseEntity<ComResp> handleFrameworkException(FrameworkException exception) {
        log.error("框架异常: ",exception);
        ComResp error = ComResp.error(exception.getCode(),exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }


    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ComResp> handleAuthException(AuthException exception) {
        log.error("权限异常: ",exception);
        ComResp error = ComResp.error(exception.getCode(),exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ComResp> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.error("Bean参数校验异常: ",exception);
        ObjectError first = exception.getBindingResult().getAllErrors().stream().findFirst().orElse(null);
        String message = "args error";
        if (ObjUtil.isNotNull(first)) {
            message = first.getDefaultMessage();
        }
        ComResp error = ComResp.error(message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ComResp> handleConstraintViolationException(ConstraintViolationException exception) {
        log.error("Method参数校验异常: ",exception);
        ConstraintViolation<?> constraintViolation = exception.getConstraintViolations().stream().findFirst().orElse(null);
        String message = "args error";
        if (ObjUtil.isNotNull(constraintViolation)) {
            message = constraintViolation.getMessageTemplate();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ComResp.error(message));
    }

    @ExceptionHandler({Throwable.class})
    public ResponseEntity<ComResp> handleException(Throwable exception) {
        log.error("未处理异常: ",exception);
        ComResp error = ComResp.error(DefaultRespCode.ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler({NoResourceFoundException.class})
    public ResponseEntity<ComResp> handleNoResourceFoundException(NoResourceFoundException exception) {
        log.error("资源不存在异常: ",exception);
        ComResp error = ComResp.error("资源不存在");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }


}

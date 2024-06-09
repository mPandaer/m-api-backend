package com.pandaer.server.aop;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.pandaer.basic.exception.FrameworkException;
import com.pandaer.basic.resp.DefaultRespCode;
import com.pandaer.basic.tools.JwtTool;
import com.pandaer.server.annotation.NoAuth;
import com.pandaer.server.exception.AuthException;
import com.pandaer.server.utils.LoginIDUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;

@Aspect
@Component
public class AuthAOP {


    @Pointcut("execution(public * com.pandaer.server.modules..controller.*.*(..))")
    public void pointCut() {}

    @Around("pointCut()")
    public Object auth(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!(joinPoint.getSignature() instanceof MethodSignature signature)) {
            throw new FrameworkException(DefaultRespCode.FRAMEWORK_ERROR);
        }
        Method method = signature.getMethod();
        NoAuth annotation = method.getAnnotation(NoAuth.class);
        if (ObjUtil.isNotNull(annotation)) {
            return joinPoint.proceed();
        }

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (ObjUtil.isNull(requestAttributes)) {
            throw new FrameworkException(DefaultRespCode.FRAMEWORK_ERROR);
        }
        HttpServletRequest request = requestAttributes.getRequest();
        if (!validAuth(request)) {
            throw new AuthException(401,"验证失败");
        }

        //将userId保存到线程上下文中
        storeLoginUserId(request);
        return joinPoint.proceed();
    }

    private void storeLoginUserId(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        JWT jwt = JWTUtil.parseToken(token);
        String userId = (String) jwt.getPayload().getClaim("userId");
        LoginIDUtil.setLoginID(userId);
    }

    private boolean validAuth(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return JwtTool.validToken(token);
    }
}

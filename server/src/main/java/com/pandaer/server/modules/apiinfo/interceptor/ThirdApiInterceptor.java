package com.pandaer.server.modules.apiinfo.interceptor;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import com.pandaer.apiclientsdk.client.ApiClient;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * todo 暂时的解决方案
 * 校验 第三方调用的API签名
 */

@Component
public class ThirdApiInterceptor implements HandlerInterceptor {
    @Resource
    private ApiClient apiClient;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        if (!validThirdApi(requestWrapper)) {
            responseWrapper.setContentType("text/plain; charset=UTF-8");
            responseWrapper.setStatus(401);
            PrintWriter writer = responseWrapper.getWriter();
            writer.println("验证失败");
            writer.flush();
            writer.close();
            responseWrapper.copyBodyToResponse();
        }
        callThirdApi(requestWrapper,responseWrapper);
        return false;
    }

    private void callThirdApi(ContentCachingRequestWrapper requestWrapper,ContentCachingResponseWrapper responseWrapper) throws IOException {
        //todo 对接口地址暂时硬编码
        String realUrl = requestWrapper.getRequestURL().toString().replace("http://localhost:8888","http://localhost:9998");
        //组装 请求参数
        Map<String,String> paramMap = new HashMap<>();
        requestWrapper.getParameterMap().forEach((k,v) -> {
            paramMap.put(k,v[0]);
        });
        String resp = "";
        if (requestWrapper.getMethod().equalsIgnoreCase("GET")) {
            resp = apiClient.get(realUrl, paramMap);

        }else {
            resp = apiClient.post(realUrl,paramMap,requestWrapper.getContentAsString());
        }
        responseWrapper.getWriter().write(resp);
        responseWrapper.copyBodyToResponse();
    }

    private boolean validThirdApi(ContentCachingRequestWrapper requestWrapper) {
        String signTemplate = "query=%s;body=%s;nonce=%s;timestamp=%s;key=%s";
        String query = requestWrapper.getQueryString();
        String body = requestWrapper.getContentAsString();
        String key = requestWrapper.getHeader("X-API-KEY");
        String nonce = requestWrapper.getHeader("X-NONCE");
        String timestamp = requestWrapper.getHeader("X-TIMESTAMP");
        String secretKey = "qwertyui";
        String originStr = String.format(signTemplate, query, body, nonce, timestamp, key);
        HMac hmac = DigestUtil.hmac(HmacAlgorithm.HmacSHA256, secretKey.getBytes(StandardCharsets.UTF_8));
        byte[] digest = hmac.digest(originStr);
        String signStr = Base64.encode(digest);
        String clientSign = requestWrapper.getHeader("X-SIGN");
        return signStr.equals(clientSign);
    }
}

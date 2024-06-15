package com.pandaer.server.modules.apiinfo.config;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 配置一些在线调试的参数
 */
@Component
@Data
public class OnlineDebugProperties {
    //Redis 缓存Key
    private String cacheKeyTemplate = "ONLINE_DEBUG:USER_ID_%s:API_ID_%s";

    //单位 秒
    private long expiredTime = 60;
    // 最大调用次数
    private int maxCallCount = 5;
}

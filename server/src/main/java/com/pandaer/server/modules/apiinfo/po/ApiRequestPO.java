package com.pandaer.server.modules.apiinfo.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "调用第三方服务接口的参数实体")
@Data
public class ApiRequestPO {

    @Schema(description = "第三方API的id")
    private String apiId;

    @Schema(description = "第三方API的URL")
    private String url;
    @Schema(description = "第三方API的方法")
    private String method;
    @Schema(description = "第三方API的请求参数")
    private String params;
    @Schema(description = "第三方API的请求体")
    private String body;
}

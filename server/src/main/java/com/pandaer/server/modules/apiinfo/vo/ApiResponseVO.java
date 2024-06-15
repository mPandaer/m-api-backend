package com.pandaer.server.modules.apiinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "第三方接口的响应体包装类")
public class ApiResponseVO {
    @Schema(description = "第三方接口的响应体内容")
    private String data;
}

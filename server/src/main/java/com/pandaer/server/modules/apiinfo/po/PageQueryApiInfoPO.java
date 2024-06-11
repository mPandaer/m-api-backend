package com.pandaer.server.modules.apiinfo.po;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "查询接口信息的参数实体")
@Data
public class PageQueryApiInfoPO {

    @Schema(description = "接口ID")
    private String apiId;

    @Schema(description = "接口名字")
    private String apiName;

    @Schema(description = "当前请求的页码")
    private Integer currentPage;

    @Schema(description = "每页的数据的个数")
    private Integer pageSize;

}

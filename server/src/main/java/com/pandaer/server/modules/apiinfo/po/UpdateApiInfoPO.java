package com.pandaer.server.modules.apiinfo.po;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;



@Schema(description = "更新接口信息的参数实体")
@Data
public class UpdateApiInfoPO {

    @Schema(description = "接口ID")
    private String apiId;

    @Schema(description = "接口名字")
    private String apiName;

    @Schema(description = "接口地址")
    private String apiUrl;

    @Schema(description = "接口请求方法")
    private String apiReqMethod;

    @Schema(description = "接口的响应体类型 PS: 响应体Content-Type的值")
    private String apiRespType;

    @Schema(description = "接口信息描述")
    private String apiDesc;

    /**
     * 接口请求参数 具体格式 [{name:xxx,value:xxx}]
     */
    @Schema(description = "接口请求头说明")
    private String apiReqHeader;

    /**
     * 接口请求参数信息 具体格式 [{name:xxx,type:xxx,required:true,desc:xxxx}]
     */
    @Schema(description = "接口请求参数说明")
    private String apiReqParams;

    /**
     * 接口响应体说明 [{name:xxxx,type:xxx,desc:xxx}]
     */
    @Schema(description = "接口响应体说明")
    private String apiRespDesc;

    /**
     * 响应体结果示例
     */
    @Schema(description = "接口响应示例")
    private String apiRespSample;


}

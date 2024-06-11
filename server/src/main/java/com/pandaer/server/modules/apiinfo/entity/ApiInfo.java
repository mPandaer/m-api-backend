package com.pandaer.server.modules.apiinfo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 
 * @TableName api_info
 */
@TableName(value ="api_info")
@Data
public class ApiInfo implements Serializable {
    /**
     * 唯一ID
     */
    @TableId(value = "api_id")
    private String apiId;

    /**
     * 接口名字
     */
    @TableField(value = "api_name")
    private String apiName;

    /**
     * 接口地址
     */
    @TableField(value = "api_url")
    private String apiUrl;

    /**
     * 接口的请求方式
     */
    @TableField(value = "api_req_method")
    private String apiReqMethod;

    /**
     * 响应体类型（响应头 Content-Type的值）
     */
    @TableField(value = "api_resp_type")
    private String apiRespType;

    /**
     * 接口描述
     */
    @TableField(value = "api_desc")
    private String apiDesc;

    /**
     * 接口请求头信息 具体格式 [{name:xxxx,value:xxx}]
     */
    @TableField(value = "api_req_header")
    private Object apiReqHeader;

    /**
     * 接口请求参数信息 具体格式 [{name:xxx,type:xxx,required:true,desc:xxxx}]
     */
    @TableField(value = "api_req_params")
    private Object apiReqParams;

    /**
     * 接口响应体说明 [{name:xxxx,type:xxx,desc:xxx}]
     */
    @TableField(value = "api_resp_desc")
    private Object apiRespDesc;

    /**
     * 响应体结果示例
     */
    @TableField(value = "api_resp_sample")
    private String apiRespSample;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
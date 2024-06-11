package com.pandaer.server.modules.apiinfo.service.impl;

import com.pandaer.server.modules.apiinfo.po.AddApiInfoPO;
import com.pandaer.server.modules.apiinfo.service.ApiInfoService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ApiInfoServiceTest {

    @Resource
    private ApiInfoService apiInfoService;


    @Test
    void addApiInfo() {
        AddApiInfoPO addApiInfoPO = new AddApiInfoPO();
        addApiInfoPO.setApiName("手机归属地查询");
        addApiInfoPO.setApiUrl("http://apis.juhe.cn/mobile/get");
        addApiInfoPO.setApiReqMethod("get");
        addApiInfoPO.setApiRespType("json/xml");
        addApiInfoPO.setApiDesc("本服务也支持私有化部署");
        addApiInfoPO.setApiReqHeader("""
                [
                    {
                        "name": "Content-Type",
                        "value": "application/x-www-form-urlencoded"
                    }
                ]""");
        addApiInfoPO.setApiReqParams("""
                [
                    {
                        "name": "phone",
                        "require": true,
                        "type": "int",
                        "desc": "需要查询的手机号或手机号前7位"
                    },
                    {
                        "name": "key",
                        "require": true,
                        "type": "string",
                        "desc": "在个人中心->我的数据,接口名称上方查看"
                    },
                    {
                        "name": "dtype",
                        "require": false,
                        "type": "string",
                        "desc": "返回数据的格式,xml或json，默认json"
                    }
                ]
                """);
        addApiInfoPO.setApiRespDesc("""
                [
                    {
                        "name": "error_code",
                        "type": "int",
                        "desc": "返回码"
                    },
                    {
                        "name": "reason",
                        "type": "string",
                        "desc": "返回说明"
                    },
                    {
                        "name": "result",
                        "type": "string",
                        "desc": "返回结果集"
                    },
                    {
                        "name": "province",
                        "type": "string",
                        "desc": "省份"
                    },
                    {
                        "name": "city",
                        "type": "string",
                        "desc": "城市, (部分记录可能为空)"
                    },
                    {
                        "name": "areacode",
                        "type": "string",
                        "desc": "区号, (部分记录可能为空)"
                    },
                    {
                        "name": "zip",
                        "type": "string",
                        "desc": "邮编, (部分记录可能为空)"
                    },
                    {
                        "name": "company",
                        "type": "string",
                        "desc": "运营商"
                    }
                ]
                """);
        addApiInfoPO.setApiRespSample("""
                {
                    "resultcode":"200",
                    "reason":"Return Successd!",
                    "result":{
                        "province":"浙江",
                        "city":"杭州",
                        "areacode":"0571",
                        "zip":"310000",
                        "company":"中国移动",
                        "card":""
                        }
                }
                """);

        apiInfoService.addApiInfo(addApiInfoPO);
    }

    @Test
    void updateApiInfo() {
    }

    @Test
    void deleteApiInfo() {
    }

    @Test
    void pageQueryApiInfo() {
    }
}
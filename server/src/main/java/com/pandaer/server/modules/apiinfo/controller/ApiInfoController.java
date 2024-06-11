package com.pandaer.server.modules.apiinfo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaer.server.modules.apiinfo.po.AddApiInfoPO;
import com.pandaer.server.modules.apiinfo.po.PageQueryApiInfoPO;
import com.pandaer.server.modules.apiinfo.po.UpdateApiInfoPO;
import com.pandaer.server.modules.apiinfo.service.ApiInfoService;
import com.pandaer.server.modules.apiinfo.vo.ApiInfoVO;
import com.pandaer.web.resp.ComResp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@Tag(name = "API信息管理服务",description = "提供API接口信息的增删改查服务")
@RestController
@RequestMapping("/api-info")
public class ApiInfoController {

    @Resource
    private ApiInfoService apiInfoService;

    @Operation(summary = "添加接口信息")
    @PostMapping("/add")
    public ComResp addApiInfo(@RequestBody AddApiInfoPO po) {
        apiInfoService.addApiInfo(po);
        return ComResp.success();
    }

    @Operation(summary = "更新接口信息")
    @PostMapping("/update")
    public ComResp updateApiInfo(@RequestBody UpdateApiInfoPO po) {
        apiInfoService.updateApiInfo(po);
        return ComResp.success();
    }

    @Operation(summary = "删除接口信息")
    @GetMapping("/delete")
    public ComResp deleteApiInfo(String apiIds) {
        apiInfoService.deleteApiInfo(apiIds);
        return ComResp.success();
    }

    @Operation(summary = "分页查询接口信息")
    @GetMapping("/page/query")
    public ComResp pageQueryApiInfo(PageQueryApiInfoPO po) {
        IPage<ApiInfoVO> page = apiInfoService.pageQueryApiInfo(po);
        return ComResp.success(page);
    }

}

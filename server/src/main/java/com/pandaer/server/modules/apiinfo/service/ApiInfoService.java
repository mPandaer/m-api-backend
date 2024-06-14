package com.pandaer.server.modules.apiinfo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pandaer.server.modules.apiinfo.entity.ApiInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pandaer.server.modules.apiinfo.po.AddApiInfoPO;
import com.pandaer.server.modules.apiinfo.po.PageQueryApiInfoPO;
import com.pandaer.server.modules.apiinfo.po.UpdateApiInfoPO;
import com.pandaer.server.modules.apiinfo.vo.ApiInfoVO;

/**
 * API接口信息服务
 */
public interface ApiInfoService extends IService<ApiInfo> {

    /**
     * 增加接口信息
     */
    void addApiInfo(AddApiInfoPO po);

    /**
     * 更新接口信息
     */
    void updateApiInfo(UpdateApiInfoPO po);

    /**
     * 删除接口信息
     */
    void deleteApiInfo(String apiIds);

    /**
     * 分页条件查询接口信息
     */
    IPage<ApiInfoVO> pageQueryApiInfo(PageQueryApiInfoPO po);

    /**
     * 上线接口
     */
    void apiOnline(String apiId);

    /**
     * 下线接口
     */
    void apiOffline(String apiId);

}

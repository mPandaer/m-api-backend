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
     * @param po
     */
    void addApiInfo(AddApiInfoPO po);

    void updateApiInfo(UpdateApiInfoPO po);

    void deleteApiInfo(String apiId);

    IPage<ApiInfoVO> pageQueryApiInfo(PageQueryApiInfoPO po);

}

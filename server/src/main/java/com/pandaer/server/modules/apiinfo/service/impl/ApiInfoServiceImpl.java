package com.pandaer.server.modules.apiinfo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandaer.apiclientsdk.client.ApiClient;
import com.pandaer.basic.exception.BusinessException;
import com.pandaer.basic.resp.IResponseCode;
import com.pandaer.basic.tools.IDTool;
import com.pandaer.server.modules.apiinfo.entity.ApiInfo;
import com.pandaer.server.modules.apiinfo.enums.ApiInfoStatus;
import com.pandaer.server.modules.apiinfo.po.AddApiInfoPO;
import com.pandaer.server.modules.apiinfo.po.ApiRequestPO;
import com.pandaer.server.modules.apiinfo.po.PageQueryApiInfoPO;
import com.pandaer.server.modules.apiinfo.po.UpdateApiInfoPO;
import com.pandaer.server.modules.apiinfo.service.ApiInfoService;
import com.pandaer.server.modules.apiinfo.mapper.ApiInfoMapper;
import com.pandaer.server.modules.apiinfo.vo.ApiInfoVO;
import com.pandaer.server.modules.apiinfo.vo.ApiResponseVO;
import com.pandaer.server.modules.user.entity.User;
import com.pandaer.server.modules.user.service.UserService;
import com.pandaer.server.utils.LoginIDUtil;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;

import static com.pandaer.server.modules.apiinfo.resp.ApiInfoRespCode.*;

/**
 * 接口信息服务
 */
@Log4j2
@Service
public class ApiInfoServiceImpl extends ServiceImpl<ApiInfoMapper, ApiInfo> implements ApiInfoService {

    @Resource
    private ApiClient apiClient;

    @Resource
    private UserService userService;

    /**
     * 添加接口信息
     * 1. 校验参数 尤其是那几个JSON格式的字符串
     * 2. 校验通过后，直接入库
     */
    @Override
    public void addApiInfo(AddApiInfoPO po) {
        if (ObjUtil.isNull(po)) {
            throw new BusinessException(ADD_API_INFO_PARAMS_EMPTY);
        }
        validApiReqHeaderFormat(po.getApiReqHeader());
        validApiReqParamsFormat(po.getApiReqParams());
        validApiRespDescFormat(po.getApiRespDesc());
        ApiInfo entity = BeanUtil.toBean(po, ApiInfo.class);
        entity.setApiId(IDTool.genID());
        if (!save(entity)) {
            throw new BusinessException(ADD_API_INFO_FAIL);
        }
    }

    /**
     * 更新接口信息
     * 1. 校验参数
     * 2. 根据ID判断接口信息是否存在
     * 3. 更新值
     */
    @Override
    public void updateApiInfo(UpdateApiInfoPO po) {
        if (ObjUtil.isNull(po)) {
            throw new BusinessException(UPDATE_API_INFO_PARAMS_EMPTY);
        }
        //校验格式
        if (StrUtil.isNotEmpty(po.getApiReqHeader())) {
            validApiReqHeaderFormat(po.getApiReqHeader());
        }
        if (StrUtil.isNotEmpty(po.getApiReqParams())) {
            validApiReqParamsFormat(po.getApiReqParams());
        }
        if (StrUtil.isNotEmpty(po.getApiRespDesc())) {
            validApiRespDescFormat(po.getApiRespDesc());
        }

        String apiId = po.getApiId();
        ApiInfo entity = getById(apiId);
        if (ObjUtil.isNull(entity)) {
            throw new BusinessException(API_INFO_NOT_EXIST);
        }
        BeanUtil.copyProperties(po,entity);
        if (!updateById(entity)) {
            throw new BusinessException(UPDATE_API_INFO_FAIL);
        }

    }

    /**
     * 逻辑删除接口信息
     * 根据apiId删除接口信息
     */
    @Transactional
    @Override
    public void deleteApiInfo(String apiIds) {
        List<String> idList = Arrays.stream(apiIds.split(",")).toList();
        if (!removeBatchByIds(idList)) {
            throw new BusinessException(DELETE_API_INFO_FAIL);
        }
    }

    @Override
    public IPage<ApiInfoVO> pageQueryApiInfo(PageQueryApiInfoPO po) {
        if (ObjUtil.isNull(po)) {
            throw new BusinessException(PAGE_QUERY_API_INFO_PARAMS_EMPTY);
        }
        LambdaQueryWrapper<ApiInfo> query = Wrappers.lambdaQuery();
        if (ObjUtil.isNotEmpty(po.getApiId())) {
            query.eq(ApiInfo::getApiId,po.getApiId());
        }
        if (ObjUtil.isNotEmpty(po.getApiName())) {
            query.likeRight(ApiInfo::getApiName,po.getApiName());
        }
        Page<ApiInfo> page = new Page<>();
        page.setCurrent(po.getCurrentPage());
        page.setSize(po.getPageSize());

        Page<ApiInfo> infoPage = page(page,query);
        List<ApiInfoVO> voList = infoPage.getRecords().stream().map(item -> BeanUtil.toBean(item, ApiInfoVO.class)).toList();

        Page<ApiInfoVO> voPage = new Page<>();
        BeanUtil.copyProperties(infoPage,voPage,"records");
        voPage.setRecords(voList);
        return voPage;
    }


    /**
     * 上线接口
     * 1. 校验参数
     * 2. 判断接受是否存在
     * 3. 判断接口是否可以调用
     * 4. 修改接口信息状态
     * @param apiId 接口ID
     */
    @Override
    public void apiOnline(String apiId) {
        if (StrUtil.isEmpty(apiId)) {
            throw new BusinessException(API_INFO_ID_EMPTY);
        }
        ApiInfo entity = getById(apiId);
        if (entity == null) {
            throw new BusinessException(API_INFO_NOT_EXIST);
        }
        //todo 判断接口是否可以调用
        entity.setApiStatus(ApiInfoStatus.ENABLE.getValue());
        if (!updateById(entity)) {
            throw new BusinessException(ONLINE_API_INFO_FAIL);
        }
    }


    /**
     * 下线接口
     * 1. 校验参数
     * 2. 判断接受是否存在
     * 3. 修改接口信息状态
     * @param apiId 接口信息ID
     */
    @Override
    public void apiOffline(String apiId) {
        if (StrUtil.isEmpty(apiId)) {
            throw new BusinessException(API_INFO_ID_EMPTY);
        }
        ApiInfo entity = getById(apiId);
        if (entity == null) {
            throw new BusinessException(API_INFO_NOT_EXIST);
        }
        entity.setApiStatus(ApiInfoStatus.DISABLE.getValue());
        if (!updateById(entity)) {
            throw new BusinessException(OFFLINE_API_INFO_FAIL);
        }
    }

    /**
     * 实现第三方API的调用
     * 1. 判断接口是否存在
     * 2. 判断用户是否开启了这个接口
     * 3. 校验第三方接口参数是否正确
     * 4. 利用自定义的SDK进行调用
     * 5. 封装返回结果
     */
    @Override
    public ApiResponseVO callApi(ApiRequestPO po) {
        if (po == null) {
            throw new BusinessException(CALL_API_EMPTY);
        }
        if (StrUtil.isEmpty(po.getApiId())) {
            throw new BusinessException(API_INFO_ID_EMPTY);
        }
        ApiInfo entity = getById(po.getApiId());
        if (entity == null) {
            throw new BusinessException(API_INFO_NOT_EXIST);
        }
        String userId = LoginIDUtil.getLoginID();
        User loginedUser = userService.getById(userId);
        //todo 校验用户是否开通了这个权限 可以设置试用次数为10次 目前只要用户存在就算有权限
        if (loginedUser == null) {
            throw new BusinessException(CALL_API_USER_NOT_EXIST);
        }
        //todo 校验接口中的参数是否满足，目前只校验 url以及method
        if (ObjectUtil.notEqual(po.getUrl(),entity.getApiUrl()) || ObjectUtil.notEqual(po.getMethod().toUpperCase(),entity.getApiReqMethod().toUpperCase())) {
            throw new BusinessException(VALID_THIRD_API_PARAM_FAIL);
        }
        String resp = null;
        try {
            resp = doCallApi(po);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //返回响应实体
        ApiResponseVO apiResponseVO = new ApiResponseVO();
        apiResponseVO.setData(resp);
        return apiResponseVO;

    }

    /**
     * 发起第三方接口调用
     * @param po
     * @return
     */
    private String doCallApi(ApiRequestPO po) throws IOException {
        //todo 对接口地址暂时硬编码
        String realUrl = po.getUrl().replace("http://api.pandaer.com","http://localhost:9998");
        //组装 请求参数
        Map<String,String> paramMap = new HashMap<>();
        if (StrUtil.isNotEmpty(po.getParams())) {
            String[] split = po.getParams().split("&");
            Arrays.stream(split).forEach((pair) -> {
                String[] entry = pair.split("=");
                paramMap.put(entry[0],entry[1]);
            });
        }
        if (po.getMethod().equalsIgnoreCase("GET")) {
            return apiClient.get(realUrl,paramMap);
        }else {
            return apiClient.post(realUrl,paramMap,po.getBody());
        }
    }


    /*===================================================private方法==============================================================*/


    /**
     * 检验接口信息的JSON格式的通用方法
     *
     * @param jsonArrayStr  jsonArray格式的字符串
     * @param predicate     校验条件
     * @param validFallCode 校验失败返回码
     */
    private void validApiInfoFormat(String jsonArrayStr, Predicate<JSONObject> predicate, IResponseCode validFallCode) {
        try {
            JSONArray jsonArray = JSONUtil.parseArray(jsonArrayStr);
            for (Object obj : jsonArray) {
                if (!(obj instanceof JSONObject)) {
                    throw new BusinessException(validFallCode);
                }
                if (predicate.test((JSONObject) obj)) {
                    throw new BusinessException(validFallCode);
                }
            }
        } catch (Exception e) {
            log.error("校验格式失败", e);
            throw new BusinessException(validFallCode);
        }
    }

    /**
     * 校验接口信息响应体说明的JSON格式
     */
    private void validApiRespDescFormat(String apiRespDesc) {
        validApiInfoFormat(apiRespDesc, (obj) -> !obj.containsKey("name") || !obj.containsKey("type") || !obj.containsKey("desc"), VALID_API_RESP_DESC_FORMAT_FAIL);
    }


    /**
     * 校验接口信息请求参数的JSON格式
     */
    private void validApiReqParamsFormat(String apiReqParams) {

        validApiInfoFormat(apiReqParams, (obj) -> !obj.containsKey("name") || !obj.containsKey("require") || !obj.containsKey("type") || !obj.containsKey("desc"), VALID_API_REQ_PARAMS_FORMAT_FAIL);
    }

    /**
     * 校验接口信息请求头的JSON格式
     */
    private void validApiReqHeaderFormat(String apiReqHeader) {
        validApiInfoFormat(apiReqHeader, (obj) -> !obj.containsKey("name") || !obj.containsKey("value"), VALID_API_REQ_HEADER_FORMAT_FAIL);

    }
}





package com.pandaer.server.modules.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pandaer.basic.exception.BusinessException;
import com.pandaer.basic.tools.IDTool;
import com.pandaer.basic.tools.JwtTool;
import com.pandaer.basic.tools.PasswdTool;
import com.pandaer.server.modules.user.entity.User;
import com.pandaer.server.modules.user.po.UserLoginPO;
import com.pandaer.server.modules.user.po.UserRegisterPO;
import com.pandaer.server.modules.user.service.UserService;
import com.pandaer.server.modules.user.mapper.UserMapper;
import com.pandaer.server.modules.user.vo.UserVO;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.pandaer.server.modules.user.resp.UserRespCode.*;



@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{


    /**
     * 1. 校验参数实体
     * 2. 判断用户是否存在
     * 3. 校验密码
     * 4. 生成Token
     *
     * @param po
     * @return
     */
    @Override
    public String userLogin(UserLoginPO po) {
        if (ObjUtil.isNull(po) || ObjUtil.hasEmpty(po.getUserAccount(),po.getUserPassword())) {
            throw new BusinessException(PARAMS_ERROR);
        }
        User entity = getUserByAccount(po.getUserAccount());
        if (ObjUtil.isNull(entity)) {
            throw new BusinessException(USER_NOT_EXIST);
        }
        if (!PasswdTool.validPasswd(po.getUserPassword(),entity.getUserPassword())) {
            throw new BusinessException(USER_PASSWORD_ERROR);
        }

        Map<String, String> payload = MapUtil.builder("userId",entity.getUserId()).build();
        return JwtTool.genToken(payload);
    }

    private User getUserByAccount(String account) {
        LambdaQueryWrapper<User> query = Wrappers.lambdaQuery();
        query.eq(User::getUserAccount, account);
        return getOne(query);
    }


    /**
     * 用户注册
     * 1. 判断账户是否已经存在
     * 2. 用户不存在就创建一个用户
     * @param po
     */
    @Override
    public void userRegister(UserRegisterPO po) {
        User entity = getUserByAccount(po.getUserAccount());
        if (ObjUtil.isNotNull(entity)) {
            throw new BusinessException(USER_HAS_EXISTED);
        }
        User user = new User();
        user.setUserId(IDTool.genID());
        user.setUserAccount(po.getUserAccount());
        user.setUserName(po.getUserAccount());
        user.setUserPassword(PasswdTool.genPasswd(po.getUserPassword()));
        user.setUserAvatarUrl("");
        //todo 生成API KEY 目前使用方法提供，后续改造成Service
        assembleApiKey(user);
        if (!save(user)) {
            throw new BusinessException(REGISTER_FAIL);
        }
    }

    /**
     * 生成API KEY
     * @param user
     */
    private void assembleApiKey(User user) {
        String accessKey = JwtTool.genToken(MapUtil.builder("userId", user.getUserId()).build());
        String secretKey = RandomUtil.randomString(16);
        user.setAccessKey(accessKey);
        user.setSecretKey(secretKey);
    }

    @Override
    public void userLogout(Long userId) {
        //todo 目前是前端实现，直接删除本地的Token，后期会考虑增加黑名单机制
    }

    @Override
    public UserVO getUserByUserId(String userId) {
        User entity = getById(userId);
        if (ObjUtil.isNull(entity)) {
            throw new BusinessException(USER_NOT_EXIST);
        }
        return BeanUtil.toBean(entity, UserVO.class);
    }
}





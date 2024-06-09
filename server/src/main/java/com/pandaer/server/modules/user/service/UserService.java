package com.pandaer.server.modules.user.service;

import com.pandaer.server.modules.user.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pandaer.server.modules.user.po.UserLoginPO;
import com.pandaer.server.modules.user.po.UserRegisterPO;
import com.pandaer.server.modules.user.vo.UserVO;


/**
 * 用户服务接口
 */
public interface UserService extends IService<User> {

    /**
     * 用户登录
     *
     * @return
     */
    String userLogin(UserLoginPO po);

    /**
     * 用户注册
     */
    void userRegister(UserRegisterPO po);

    /**
     * 用户登出
     */
    void userLogout(Long userId);

    /**
     * 获取当前用户登录信息
     */
    UserVO getUserByUserId(String userId);
}

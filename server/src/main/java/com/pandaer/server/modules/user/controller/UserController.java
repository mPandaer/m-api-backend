package com.pandaer.server.modules.user.controller;

import com.pandaer.server.annotation.NoAuth;
import com.pandaer.server.modules.user.po.UserLoginPO;
import com.pandaer.server.modules.user.po.UserRegisterPO;
import com.pandaer.server.modules.user.service.UserService;
import com.pandaer.server.modules.user.vo.UserVO;
import com.pandaer.server.utils.LoginIDUtil;
import com.pandaer.web.resp.ComResp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@Tag(name = "用户服务",description = "提供用户登录，注册，获取当前登录用户的功能")
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @NoAuth
    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public ComResp userLogin(@Validated @RequestBody UserLoginPO po) {
        String token = userService.userLogin(po);
        return ComResp.success(token);
    }

    @NoAuth
    @PostMapping("/register")
    @Operation(summary = "用户注册")
    public ComResp userRegister(@Validated @RequestBody UserRegisterPO po) {
        userService.userRegister(po);
        return ComResp.success();
    }

    @GetMapping("/current")
    @Operation(summary = "获取当前登录的用户信息")
    public ComResp getCurrentLoginUser() {
        UserVO userVO = userService.getUserByUserId(LoginIDUtil.getLoginID());
        return ComResp.success(userVO);
    }

}

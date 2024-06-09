package com.pandaer.server.modules.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 用户表
 * @TableName users
 */
@TableName(value ="users")
@Data
public class User implements Serializable {
    /**
     * 用户ID
     */
    @TableId(value = "user_id")
    private String userId;

    /**
     * 账户
     */
    @TableField(value = "user_account")
    private String userAccount;

    /**
     * 用户名
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 用户密码
     */
    @TableField(value = "user_password")
    private String userPassword;

    /**
     * 用户头像链接
     */
    @TableField(value = "user_avatar_url")
    private String userAvatarUrl;


    /**
     * 用户角色
     */
    @TableField(value = "user_role")
    private String userRole;


    /**
     * 用户删除标识 0--表示未删除 1--已删除
     */
    @TableField(value = "is_delete")
    private Integer isDelete;

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
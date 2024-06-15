package com.pandaer.server.modules.user.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pandaer.server.serializer.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "用户响应实体")
@Data
public class UserVO {

    @Schema(description = "用户ID")
    private String userId;

    @Schema(description = "用户账户")
    private String userAccount;

    @Schema(description = "用户名")
    private String userName;

    @Schema(description = "用户头像链接")
    private String userAvatarUrl;

    @Schema(description = "用户角色")
    private String userRole;

    @Schema(description = "AccessKey")
    private String accessKey;

    @Schema(description = "SecretKey")
    private String secretKey;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Schema(description = "用户创建时间")
    private LocalDateTime createTime;

}

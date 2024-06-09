package com.pandaer.server.modules.user.po;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


@Schema(description = "用户登录参数实体")
@Data
public class UserLoginPO {

    @NotEmpty(message = "用户账户不能为空")
    @Schema(description = "用户账户")
    private String userAccount;

    @NotEmpty(message = "用户密码不能为空")
    @Length(min = 6,max = 32,message = "密码必须在6~32位之间")
    @Schema(description = "用户密码")
    private String userPassword;
}

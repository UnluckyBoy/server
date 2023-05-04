package com.server.backTool.dataUtils;

import lombok.Data;

/**
 * @ClassName LoginToken
 * @Author Create By matrix
 * @Date 2023/5/2 0002 19:26
 */
@Data
public class LoginToken {
    //用户存储登录者账户
    private String account;
    //唯一token码
    private String token;
}

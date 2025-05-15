package com.example.system.demos.web.manageUser.register;

import java.util.Map;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * 注册的封装
 */
public interface RegisterController {
    /**
     * 注册
     * @param account 邮箱或者手机号
     * @param password 密码
     * @return 返回注册结果
     */
    Map<String,Object> register(String account,String password);
}

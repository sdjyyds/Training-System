package com.example.system.demos.web.manageUser.register;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * 注册服务的封装
 */
public interface RegisterService {
    /**
     * 注册的服务
     * @param account 邮箱或者手机号
     * @param password 密码
     * @return 注册是否成功
     */
    boolean register(String account, String password);
}

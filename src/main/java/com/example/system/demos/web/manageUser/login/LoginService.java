package com.example.system.demos.web.manageUser.login;

import com.example.system.jdbc.entity.User;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * 登录服务的封装类
 */
public interface LoginService {
    /**
     * 验证用户身份
     * @param account 手机号或邮箱
     * @param password 密码
     * @return User
     */
    User verifyIdentity(String account, String password);

    /**
     * 登录前的操作
     * @param user 用户
     * @param response 后端响应
     * @throws IOException IO异常对象
     */
    void beforeLogin(User user, HttpServletResponse response) throws IOException;
    boolean validateToken(String token);
    String extractLoginFromToken(String token);
}

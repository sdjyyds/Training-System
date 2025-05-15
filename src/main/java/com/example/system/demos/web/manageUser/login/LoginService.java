package com.example.system.demos.web.manageUser.login;

import com.example.system.jdbc.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
     * @param request 前端请求
     * @param response 后端响应
     * @throws IOException IO异常对象
     */
    void beforeLogin(User user, HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * 创建HttpSession
     * @param login 登录名
     * @param token 安全token
     * @param request 前端请求
     * @param session 会话信息
     * @return 返回是否创建成功
     */
    boolean createHttpSession(String login, String token, HttpServletRequest request, HttpSession session);
}

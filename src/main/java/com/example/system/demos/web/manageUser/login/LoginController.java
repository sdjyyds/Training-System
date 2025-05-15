package com.example.system.demos.web.manageUser.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * 登录的封装
 */
public interface LoginController {
    /**
     * 登录
     * @param request 前端请求
     * @param response 后端响应
     * @throws IOException IO异常
     */
    void login(HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * 自动登录
     * @param request 前端请求
     * @return 返回是否自动登录成功
     */
    String autoLogin(HttpServletRequest request);
}

package com.example.system.demos.web.manageUser.login;

import com.example.system.demos.web.manageUser.util.MD5Utils;
import com.example.system.jdbc.entity.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * 完成用户登录的实现
 */
@RestController
@Slf4j
public class LoginController {

    @Autowired
    private LoginService loginServixe;

    /**
     * 用户登录接口
     * 1. 接收前端传来的账号和密码
     * 2. 使用 MD5Utils 工具类对密码进行加密处理
     * 3. 调用 loginServixe.verifyIdentity 方法验证用户身份
     * 4. 如果验证成功：
     *    - 执行登录前处理（生成 JWT Token 并设置到 Cookie）
     *    - 记录日志并重定向到首页
     * 5. 如果验证失败，重定向回登录页并携带错误参数
     */
    @PostMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String account = request.getParameter("account");
        String password = request.getParameter("password");

        // 对密码进行 MD5 加密
        password = MD5Utils.generateWay(password);
        // 验证用户身份
        User user = loginServixe.verifyIdentity(account, password);
        log.info("user: {}", user);
        if (user != null) {
            // 登录前处理：生成 Token 并设置到 Cookie
            loginServixe.beforeLogin(user, response);
            log.info("登录成功！");
            // 重定向到首页
            response.sendRedirect("index/homeIndex.html");
        } else {
            // 登录失败，重定向回登录页并提示错误
            response.sendRedirect("login/login.html?error=1");
        }
    }

    /**
     * 自动登录接口（基于 JWT Token）
     * 1. 从请求中获取 Cookie
     * 2. 查找名为 "token" 的 Cookie 值
     * 3. 如果 Token 存在且有效：
     *    - 解析 Token 获取用户信息
     *    - 返回成功状态及用户信息和跳转路径
     * 4. 否则返回失败状态
     */
    @GetMapping("/autoLogin")
    public String autoLogin(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = null;

        // 查找 Token
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
        }

        // 验证 Token 并返回结果
        if (token != null && loginServixe.validateToken(token)) {
            String login = loginServixe.extractLoginFromToken(token);
            return "{\"status\": \"success\", \"user\": \"" + login + "\"}";
        }
        return "{\"status\": \"fail\"}";
    }

    /**
     * 注销接口
     * 1. 创建一个同名但空值的 Token Cookie，并将 MaxAge 设置为 0 以删除原有 Token
     * 2. 重定向回登录页面
     * 3. 记录注销日志
     */
    @GetMapping("/logout")
    public void logout(HttpServletResponse response) throws IOException {
        // 删除 Token Cookie
        Cookie cookie = new Cookie("token", "");
        cookie.setMaxAge(0); // 立即过期
        cookie.setPath("/"); // 确保与原 Token 路径一致
        response.addCookie(cookie);

        // 重定向回登录页
        response.sendRedirect("/login/login.html");
        log.info("注销成功");
    }
}

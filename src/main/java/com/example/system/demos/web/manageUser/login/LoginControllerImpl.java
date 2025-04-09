package com.example.system.demos.web.manageUser.login;

import com.example.system.demos.web.manageUser.util.MD5Utils;
import com.example.system.jdbc.dao.UserDao;
import com.example.system.jdbc.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import static com.example.system.demos.web.manageUser.util.MD5Utils.generateWay;
@RestController
public class LoginControllerImpl implements LoginController{
    @Autowired
    @Lazy
    private UserDao userDao;
    // 处理用户登录
    @PostMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        System.out.println("account:" + account + ",password:" + password);
        //加密匹配
        password = MD5Utils.generateWay(password);
        System.out.println("password:" + password);
        //其他某个具体的entity类 -- 需要的信息多了个密码字段？不需要,直接传，我不期望收到密码这类敏感信息
        User user = null;
        // 1. 验证用户身份
        if (account.contains("@")) user = userDao.findByEmailAndPassword(account, password);
        else user = userDao.findByPhoneAndPassword(account, password);
        System.out.println("user:" + user);
        if (user != null) {
            //2.创建或者获取httpSession维护一些必要信息--设置过期时间
            HttpSession session = request.getSession();
            session.setAttribute("user", user.getId());
            session.setAttribute("username", user.getUsername());
            //session.setAttribute("",);
            session.setMaxInactiveInterval(30 * 60);
            System.out.println("session:" + session);
            //3.生成token
            String token = generateWay(String.valueOf(user.getId()));
            //4.存储安全token
            Cookie loginCookie = new Cookie("login", String.valueOf(user.getId()));
            loginCookie.setMaxAge(7 * 60 * 60 * 24);
            loginCookie.setPath("/");
            Cookie tokenCookie = new Cookie("token", token);
            tokenCookie.setMaxAge(7 * 60 * 60 * 24);
            tokenCookie.setHttpOnly(true);
            loginCookie.setPath("/");
            response.addCookie(loginCookie);
            response.addCookie(tokenCookie);
            response.sendRedirect("index/homeIndex.html");
        } else {
            response.sendRedirect("login/login.html?error=1");
        }
    }




    @RequestMapping("/autoLogin")
    public String autoLogin(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 获取现有 Session，不创建新的
        if (session != null && session.getAttribute("user") != null) {
            System.out.println("session:" + session);
            System.out.println(session.getAttribute("user"));
            System.out.println(session.getMaxInactiveInterval());
            return "{\"status\": \"success\", \"user\": \"" + session.getAttribute("user") + "\", \"redirect\": \"index/homeIndex.html\"}";
        }

        // Session 失效，检查 Cookie["login"] 和 Cookie["token"]
        String login = null, token = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("login".equals(cookie.getName())) {
                    login = cookie.getValue();
                } else if ("token".equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
        }
        System.out.println("login:" + login + ",token:" + token);
        // 验证 Token
        if (login != null && token != null && token.equals(generateWay(login))) {
            session = request.getSession();
            session.setAttribute("user", login);
            session.setAttribute("username",userDao.selectUserNameById(Integer.valueOf(login)));
            session.setMaxInactiveInterval(30 * 60); // 30 分钟
            System.out.println("session:" + session);
            System.out.println("session.getAttribute(\"user\"): " + session.getAttribute("user"));
            System.out.println(session.getAttribute("user"));
            System.out.println("session.getAttribute(\"username\"): " + session.getAttribute("username"));
            System.out.println(session.getAttribute("username"));
            return "{\"status\": \"success\", \"user\": \"" + login + "\", \"redirect\": \"index/homeIndex.html\"}";
        }

        return "{\"status\": \"fail\"}";
    }


    @RequestMapping("/logout")
    public void logout(HttpServletRequest request,HttpServletResponse response) throws IOException {

        // 销毁服务器内存 session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        //删除 Cookie
        Cookie cookie = new Cookie("login", "");
        cookie.setMaxAge(0); // 立即删除
        cookie.setPath("/");
        Cookie cookie1 = new Cookie("token", "");
        cookie1.setMaxAge(0); // 立即删除
        cookie1.setPath("/");
        response.addCookie(cookie);
        response.addCookie(cookie1);
        response.sendRedirect("/login/login.html");
        System.out.println("删除成功！");
    }
}

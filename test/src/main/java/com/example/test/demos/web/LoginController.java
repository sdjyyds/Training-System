package com.example.test.demos.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.MessageDigest;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@RestController
public class LoginController {

    private static final String SECRET_KEY = "fff4324324"; // 服务器密钥（实际开发中应存入配置）

    // 生成一个简单的 Token（这里用 MD5 作为示例，实际可使用更安全的 HMAC）
    private String generateToken(String username) {
        return MD5Utils.md5(username + SECRET_KEY);
    }

    @RequestMapping("/autoLogin")
    @ResponseBody
    public String autoLogin(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 获取现有 Session，不创建新的
        if (session != null && session.getAttribute("user") != null) {
            return "{\"status\": \"success\", \"user\": \"" + session.getAttribute("user") + "\", \"redirect\": \"/index.html\"}";
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

        // 验证 Token
        if (login != null && token != null && token.equals(generateToken(login))) {
            session = request.getSession();
            session.setAttribute("user", login);
            session.setMaxInactiveInterval(30 * 60); // 30 分钟
            return "{\"status\": \"success\", \"user\": \"" + login + "\", \"redirect\": \"/index.html\"}";
        }

        return "{\"status\": \"fail\"}";
    }

    @RequestMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if ("admin".equals(username) && "abc123".equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", username);
            session.setMaxInactiveInterval(30 * 60);

            // 生成 Token
            String token = generateToken(username);

            // 存储安全 Cookie
            Cookie loginCookie = new Cookie("login", username);
            loginCookie.setMaxAge(7 * 24 * 60 * 60);
            loginCookie.setPath("/");

            Cookie tokenCookie = new Cookie("token", token);
            tokenCookie.setMaxAge(7 * 24 * 60 * 60);
            tokenCookie.setPath("/");
            tokenCookie.setHttpOnly(true); // 防止 JS 访问

            response.addCookie(loginCookie);
            response.addCookie(tokenCookie);
            response.sendRedirect("/index.html");
        } else {
            response.sendRedirect("/login.html?error=1");
        }
    }
}

class MD5Utils {
    public static String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

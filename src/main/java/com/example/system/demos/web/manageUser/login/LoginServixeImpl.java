package com.example.system.demos.web.manageUser.login;

import com.example.system.demos.web.manageUser.util.JwtUtils;
import com.example.system.jdbc.dao.UserDao;
import com.example.system.jdbc.entity.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@Service
public class LoginServixeImpl implements LoginService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 根据账号和密码验证用户身份
     * 如果账号包含 "@"，则通过邮箱和密码验证用户；否则，通过手机号和密码验证用户
     *
     * @param account 用户账号，可以是邮箱或手机号
     * @param password 用户密码
     * @return 如果验证成功，返回用户对象；否则，返回null
     */
    public User verifyIdentity(String account, String password) {
        if (account.contains("@")) return userDao.findByEmailAndPassword(account, password);
        else return userDao.findByPhoneAndPassword(account, password);
    }

    /**
     * 登录前的预处理，包括生成JWT token并设置到Cookie中
     *
     * @param user 验证身份成功后的用户对象
     * @param response HTTP响应对象，用于设置Cookie
     */
    public void beforeLogin(User user, HttpServletResponse response) {
        String token = jwtUtils.generateToken(String.valueOf(user.getId()));
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(7 * 24 * 60 * 60); // 7天
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 验证JWT token的有效性
     *
     * @param token 待验证的JWT token
     * @return 如果token有效，返回true；否则，返回false
     */
    public boolean validateToken(String token) {
        return jwtUtils.validateToken(token);
    }

    /**
     * 从JWT token中提取用户登录名
     *
     * @param token 包含用户信息的JWT token
     * @return 用户登录名
     */
    public String extractLoginFromToken(String token) {
        return jwtUtils.parseToken(token);
    }
}



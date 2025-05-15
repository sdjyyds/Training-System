package com.example.system.demos.web.manageUser.login;

import com.example.system.jdbc.dao.UserDao;
import com.example.system.jdbc.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static com.example.system.demos.web.manageUser.util.MD5Utils.generateWay;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@Service
public class LoginServixeImpl implements LoginService {
    @Autowired
    private UserDao userDao;

    /**
     * 验证用户身份
     * @param account  手机号或邮箱
     * @param password 密码
     * @return User
     */
    public User verifyIdentity(String account, String password) {
        User user;
        if (account.contains("@")) user = userDao.findByEmailAndPassword(account, password);
        else user = userDao.findByPhoneAndPassword(account, password);
        return user;
    }

    /**
     * 处理用户登录
     * @param user 用户
     * @param request 前端请求
     * @param response 后端响应
     */
    public void beforeLogin(User user, HttpServletRequest request, HttpServletResponse response){
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

        System.out.println("完成创建！");
    }

    /**
     * 创建httpSession
     * @param login 登录名
     * @param token 安全token
     * @param request 前端请求
     * @param session 会话信息
     * @return 返回是否创建成功
     */
    public boolean createHttpSession(String login, String token, HttpServletRequest request, HttpSession session) {
        if (login != null && token != null && token.equals(generateWay(login))) {
            session = request.getSession();
            session.setAttribute("user", login);
            session.setAttribute("username", userDao.selectUserNameById(Integer.valueOf(login)));
            session.setMaxInactiveInterval(30 * 60); // 30 分钟
            return true;
        }
        return false;
    }
}

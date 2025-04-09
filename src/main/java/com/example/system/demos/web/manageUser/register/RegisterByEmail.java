package com.example.system.demos.web.manageUser.register;

import com.example.system.demos.web.manageUser.util.MD5Utils;
import com.example.system.jdbc.dao.UserDao;
import com.example.system.jdbc.dao.UserSensitiveDao;
import com.example.system.jdbc.entity.User;
import com.example.system.jdbc.entity.UserSensitive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@RestController
public class RegisterByEmail implements RegisterController{
    @Autowired
    @Lazy
    private UserDao userDao;
    @Autowired
    @Lazy
    private UserSensitiveDao userSensitiveDao;
    @Autowired
    private User  user;
    @Autowired
    private UserSensitive userSensitive;
    @Override
    @RequestMapping("/registerByEmail")
    public Map<String, Object> register(@RequestParam("email") String content, String password) {
        Map<String, Object> result = new HashMap();
        //查询其是否存在于数据库中
        Boolean flag = userDao.existEmail(content);
        //注册失败就返回false
        if (flag) {
            result.put("success", false);
            result.put("message", "该邮箱已被注册！");
            return result;
        }
        //注册成功返回true--一些必要的字段完成初始化
        //需要分配Id号和其他必要字段--业务逻辑
        //创建用户，分配用户的基本信息 -- 考虑采用bean管理对象的创建
        user.setEmail(content);
        userDao.insertSelective(user);
        userSensitive.setUserId(userDao.selectPrimaryKeyByEmail(content));
        String passwordHash = MD5Utils.generateWay(password);
        userSensitive.setPasswordHash(passwordHash);
        userSensitiveDao.insertSelective(userSensitive);
        System.out.println("邮箱为：" + content + "的用户注册成功");
        System.out.println("该用户的ID为：" + userDao.selectPrimaryKeyByEmail(content));
        // 返回成功消息
        result.put("success", true);
        result.put("message", "注册成功！");
        return result;
    }
}

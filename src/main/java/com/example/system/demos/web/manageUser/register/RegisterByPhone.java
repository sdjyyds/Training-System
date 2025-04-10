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

import java.util.Map;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@RestController  // 让 Spring 直接返回 JSON，而不是视图
public class RegisterByPhone implements RegisterController{
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
    @RequestMapping("/registerByPhone")
    public Map<String,Object> register(@RequestParam("phone") String content,String password) {
        Map<String,Object> result = new java.util.HashMap();
        //查询其是否存在于数据库中
        Boolean flag = userDao.existPhone(content);
        //注册失败就返回false
        if(flag){
            result.put("success", false);
            result.put("message", "该手机号已被注册！");
            return result;
        }
        //注册成功返回true--一些必要的字段完成初始化
        //需要分配Id号和其他必要字段--业务逻辑
        //简单处理，只负责分配手机号
        user.setPhone(content);
        userDao.insertSelective(user);
        Integer userId = userDao.selectPrimaryKeyByPhone(content);
        userSensitive.setUserId(userId);
        System.out.println(userId);
        String passwordHash = MD5Utils.generateWay(password);
        userSensitive.setPasswordHash(passwordHash);
        userSensitiveDao.insertSelective(userSensitive);
        System.out.println("手机号为：" + content + "的用户注册成功");
        System.out.println("该用户的ID为：" + userId);
        // 返回成功消息
        result.put("success", true);
        result.put("message", "注册成功！");
        return result;
    }
}

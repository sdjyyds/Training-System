// 包名和导入的类
package com.example.system.demos.web.manageUser.register;

import com.example.system.demos.web.manageUser.util.MD5Utils;
import com.example.system.jdbc.dao.UserDao;
import com.example.system.jdbc.dao.UserSensitiveDao;
import com.example.system.jdbc.entity.User;
import com.example.system.jdbc.entity.UserSensitive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * RegisterService的实现类
 */
// 服务类注解，表示这是一个服务类
@Service
public class RegisterServiceImpl implements RegisterService {
    // 自动注入UserDao实例，使用懒加载
    @Autowired
    private UserDao userDao;
    // 自动注入UserSensitiveDao实例，使用懒加载
    @Autowired
    private UserSensitiveDao userSensitiveDao;
    // 自动注入User实例
    @Autowired
    private User user;
    // 自动注入UserSensitive实例
    @Autowired
    private UserSensitive userSensitive;

    /**
     * 注册用户
     * @param account 手机号或者邮箱
     * @param password 密码
     * @return 是否注册成功
     */
    public boolean register(String account, String password) {
        Boolean flag;
        if(account.contains("@")){
            flag = userDao.existEmail(account);
        }else{
            flag = userDao.existPhone(account);
        }
        // 如果手机号或者已经存在，则注册失败
        if (flag) {
            return false;
        }
        // 设置用户手机号或者邮箱
        if(account.contains("@"))user.setEmail(account);
        else user.setPhone(account);
        // 插入用户信息到数据库
        userDao.insertSelective(user);
        // 获取用户ID
        Integer userId = userDao.selectPrimaryKeyByPhone(account);
        // 设置用户敏感信息的用户ID
        userSensitive.setUserId(userId);
        // 对密码进行MD5加密
        String passwordHash = MD5Utils.generateWay(password);
        // 设置用户敏感信息的密码
        userSensitive.setPasswordHash(passwordHash);
        // 插入用户敏感信息到数据库
        userSensitiveDao.insertSelective(userSensitive);
        // 输出注册成功信息
        System.out.println("手机号为：" + account + "的用户注册成功");
        System.out.println("该用户的ID为：" + userId);
        // 返回注册成功
        return true;
    }
}
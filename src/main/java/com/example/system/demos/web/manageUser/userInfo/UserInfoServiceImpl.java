package com.example.system.demos.web.manageUser.userInfo;

import com.example.system.demos.web.manageUser.util.MD5Utils;
import com.example.system.jdbc.dao.UserDao;
import com.example.system.jdbc.dao.UserSensitiveDao;
import com.example.system.jdbc.entity.User;
import com.example.system.jdbc.entity.UserSensitive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * UserInfoService的实现类
 */
@Slf4j
@Service
public class UserInfoServiceImpl implements UserInfoService{
    //依赖注入user操作类
    @Autowired
    private UserDao userDao;
    //依赖注入userSensitive操作类
    @Autowired
    private UserSensitiveDao userSensitiveDao;

    /**
     * 1.获取User信息
     * @param id userId
     * @return User
     */
    public User getUserInfo(Integer id) {
        return userDao.selectByPrimaryKey(id);
    }

    /**
     * 2.更新User
     * @param user 用户的实体
     * @return boolean
     */
    public boolean updateUserInfo(User user) {
        return userDao.updateByPrimaryKeySelective(user) > 0;
    }

    /**
     * 3.判断userId和password
     * @param userId userId
     * @param password password
     * @return boolean
     */
    public boolean judgeUserIdAndPassword(Integer userId, String password) {
        UserSensitive userSensitive = userSensitiveDao.selectByPrimaryKey(userId);
        if (userSensitive == null) return false;
        return userSensitive.getPasswordHash().equals(MD5Utils.generateWay(password));
    }

    /**
     * 4.更新UserSensitive
     * @param userId user的id
     * @param password password
     * @return boolean
     */
    public boolean updatePassword(Integer userId, String password) {
        UserSensitive userSensitive = userSensitiveDao.selectByPrimaryKey(userId);
        if (userSensitive == null) return false;
        userSensitive.setPasswordHash(MD5Utils.generateWay(password));
        return userSensitiveDao.updateByPrimaryKeySelective(userSensitive) > 0;
    }

    /**
     * 5.判断邮箱或手机号是否是对应用户的
     * @param userId user的id
     * @param emailOrPhone 邮箱或手机号
     * @param type email or phone
     * @return boolean
     */
    public boolean judgeEmailOrPhone(Integer userId, String emailOrPhone, String type) {
        if ("email".equals(type)) {
            return userId.equals(userDao.selectPrimaryKeyByEmail(emailOrPhone));
        } else if ("phone".equals(type)) {
            return userId.equals(userDao.selectPrimaryKeyByPhone(emailOrPhone));
        }
        System.out.println("type error");
        return false;
    }

    /**
     * 返回用户对应的邮箱或者手机号
     * @param id user的id
     * @param type email or phone
     * @return String
     */
    public String selectEmailOrPhone(Integer id, String type) {
        if ("email".equals(type)) {
            return userDao.selectByPrimaryKey(id).getEmail();
        } else if ("phone".equals(type)) {
            return userDao.selectByPrimaryKey(id).getPhone();
        }
        log.error("type error");
        return null;
    }
}

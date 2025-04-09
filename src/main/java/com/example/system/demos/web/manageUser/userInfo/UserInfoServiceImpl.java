package com.example.system.demos.web.manageUser.userInfo;

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
 */
@Service
public class UserInfoServiceImpl implements UserInfoService{

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserSensitiveDao userSensitiveDao;

    /**
     * 1.查询User表整个字段
     *
     * @param id
     * @return
     */
    public User getUserInfo(Integer id) {
        return userDao.selectByPrimaryKey(id);
    }

    /**
     * 2.更新User表
     *
     * @param user
     * @return
     */
    public boolean updateUserInfo(User user) {
        return userDao.updateByPrimaryKeySelective(user) > 0;
    }

    /**
     * 3.判断UserSensitive中userId和password是否对了
     *
     * @param userId
     * @param password
     * @return
     */
    public boolean judgeUserIdAndPassword(Integer userId, String password) {
        UserSensitive userSensitive = userSensitiveDao.selectByPrimaryKey(userId);
        if (userSensitive == null) return false;
        return userSensitive.getPasswordHash().equals(MD5Utils.generateWay(password));
    }

    /**
     * 4.更新UserSensitive
     * @param userId
     * @param password
     * @return
     */
    public boolean updatePassword(Integer userId, String password) {
        UserSensitive userSensitive = userSensitiveDao.selectByPrimaryKey(userId);
        if (userSensitive == null) return false;
        userSensitive.setPasswordHash(MD5Utils.generateWay(password));
        return userSensitiveDao.updateByPrimaryKeySelective(userSensitive) > 0;
    }

    /**
     * 5.判断邮箱或手机号是否是对应用户的
     * @param userId
     * @param emailOrPhone
     * @param type
     * @return
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
     * @param id
     * @param type
     * @return
     */
    public String selectEmailOrPhone(Integer id, String type) {
        if ("email".equals(type)) {
            return userDao.selectByPrimaryKey(id).getEmail();
        } else if ("phone".equals(type)) {
            return userDao.selectByPrimaryKey(id).getPhone();
        }
        System.out.println("type error");
        return null;
    }
}

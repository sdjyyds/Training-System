package com.example.system.demos.web.manageUser.userInfo;

import com.example.system.jdbc.entity.User;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
public interface UserInfoService {
    User getUserInfo(Integer id);

    boolean updateUserInfo(User user);

    boolean judgeUserIdAndPassword(Integer userId,String password);

    boolean updatePassword(Integer userId,String password);
    boolean judgeEmailOrPhone(Integer userId, String emailOrPhone, String type);
    String selectEmailOrPhone(Integer id, String type);
}

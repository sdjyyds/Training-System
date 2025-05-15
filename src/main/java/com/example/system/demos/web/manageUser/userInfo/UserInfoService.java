package com.example.system.demos.web.manageUser.userInfo;

import com.example.system.jdbc.entity.User;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * 用户信息
 */
public interface UserInfoService {
    /**
     * 1.查询User表整个字段
     * @param id userId
     * @return 返回用户的基本信息
     */
    User getUserInfo(Integer id);

    /**
     * 2.更新User表
     * @param user 用户的实体
     * @return 返回是否更新成功
     */
    boolean updateUserInfo(User user);

    /**
     * 3.判断UserSensitive中userId和password是否对了
     * @param userId userId
     * @param password password
     * @return 返回是否对
     */
    boolean judgeUserIdAndPassword(Integer userId,String password);

    /**
     * 4.更新UserSensitive
     * @param userId userId
     * @param password password
     * @return 返回是否更新成功
     */
    boolean updatePassword(Integer userId,String password);

    /**
     * 5.判断邮箱或者手机号是否存在
     * @param userId userId
     * @param emailOrPhone emailOrPhone
     * @param type type
     * @return 返回是否存在
     */
    boolean judgeEmailOrPhone(Integer userId, String emailOrPhone, String type);

    /**
     * 6.查询邮箱或者手机号
     * @param id 用户的id
     * @param type email或者phone
     * @return 返回邮箱或者手机号
     */
    String selectEmailOrPhone(Integer id, String type);
}

package com.example.system.demos.web.manageUser.userInfo;

import com.example.system.jdbc.entity.User;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Map;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * 用户的基本信息
 */
public interface UserInfoController {
    /**
     * 1.显示用户的基本信息
     * @return
     */
    User showUserInfo(Integer userId);

    /**
     * 2.更新用户的基本信息
     * @param user
     * @return
     */
    String updateUserInfo(User user);

    /**
     * 3.查询邮箱或者手机号
     * @param body
     * @return
     */
   String showEmailOrPhone(Map<String, String> body);

    /**
     *4.判断邮箱或者手机号是否存在
     * @param body
     * @return
     */
    String judgeEmailOrPhone(@RequestBody Map<String, String> body);

    /**
     * 5.更新密码
     * @param map
     * @return
     */
   String updatePassword(Map<String,String> map);
}

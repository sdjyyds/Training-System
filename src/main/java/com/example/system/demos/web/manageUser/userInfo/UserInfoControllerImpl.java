package com.example.system.demos.web.manageUser.userInfo;

import com.example.system.jdbc.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * 用户信息控制的实现
 */
@RestController
@RequestMapping("/userInfo")
public class UserInfoControllerImpl implements UserInfoController {
    //依赖注入对应的服务类
    @Autowired
    UserInfoServiceImpl userInfoService;
    //依赖注入user
    @Autowired
    private User user;

    /**
     * 1.显示用户的基本信息
     * @param userId 用户的id
     * @return 返回用户的基本信息
     */
    @Override
    @GetMapping("/getInfo")
    public User showUserInfo(Integer userId) {
        return userInfoService.getUserInfo(userId);
    }

    /**
     * 2.更新用户的基本信息
     * @param user 用户的实体
     * @return 返回更新结果
     */
    @Override
    @PostMapping("/updateInfo")
    public String updateUserInfo(@RequestBody User user) {
        System.out.println(user);
        return userInfoService.updateUserInfo(user) ? "{\"status\": \"success\"}" : "{\"status\": \"fail\"}";
    }

    /**
     * 3.查询邮箱或者手机号
     * @param body 接收参数
     * @return 返回邮箱或者手机号
     */
    @Override
    @PostMapping("/getEmailOrPhone")
    public String showEmailOrPhone(@RequestBody Map<String, String> body) {
        String userId = body.get("userId");
        String password = body.get("password");
        String type = body.get("type");
        if(userId == null || password == null || type == null || userId.length() == 0 || password.length() == 0 || type.length() == 0)
            return "{\"status\": \"fail\"}";
        String value = null;
        System.out.println("userId:" + userId + ",password:" + password + ",type:" + type);
        //1.验证密码合法性
        if (userInfoService.judgeUserIdAndPassword(Integer.parseInt(userId), password)) {
            //2.查询邮箱或者手机号
            value = userInfoService.selectEmailOrPhone(Integer.parseInt(userId), type);
            return "{\"status\": \"success\", \"value\": \"" + value + "\"}";
        }
        return "{\"status\": \"fail\", \"value\": \"" + value + "\"}";
    }

    /**
     * 4.判断邮箱或者手机号是否存在
     * @param body 接收前端参数
     * @return 返回判断结果
     */
    @Override
    @PostMapping("/verifyEmailOrPhone")
    public String judgeEmailOrPhone(@RequestBody Map<String, String> body) {
        String userId = body.get("userId");
        String emailOrPhone = body.get("emailOrPhone");
        if (userId == null || emailOrPhone == null || userId.length() == 0 || emailOrPhone.length() == 0)
            return "{\"status\": \"fail\"}";
        String type = body.get("type");
        if (userInfoService.judgeEmailOrPhone(Integer.parseInt(userId), emailOrPhone, type))
            return "{\"status\": \"success\"}";
        return "{\"status\": \"fail\"}";
    }

    /**
     * 5.更新密码
     * @param map 接受前端传来的参数
     * @return 返回更新结果
     */
    @Override
    @PostMapping("/resetPassword")
    public String updatePassword(@RequestBody Map<String, String> map) {
        String userId = map.get("userId");
        String newPassword = map.get("newPassword");
        System.out.println("userId:" + userId + ",newPassword:" + newPassword);
        if (userId == null || newPassword == null || userId.length() == 0 || newPassword.length() == 0)
            return "{\"status\": \"fail\"}";
        if (userInfoService.updatePassword(Integer.parseInt(userId), newPassword))
            return "{\"status\": \"success\"}";
        return "{\"status\": \"fail\"}";
    }
}

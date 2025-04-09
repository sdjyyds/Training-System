package com.example.system.demos.web.manageUser.userInfo;

import com.example.system.jdbc.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@RestController
@RequestMapping("/userInfo")
public class UserInfoControllerImpl implements UserInfoController {
    @Autowired
    UserInfoServiceImpl userInfoService;
    @Autowired
    private User user;

    @Override
    @GetMapping("/getInfo")
    public User showUserInfo(Integer userId) {
        return userInfoService.getUserInfo(userId);
    }

    @Override
    @PostMapping("/updateInfo")
    public String updateUserInfo(@RequestBody User user) {
        System.out.println(user);
        return userInfoService.updateUserInfo(user) ? "{\"status\": \"success\"}" : "{\"status\": \"fail\"}";
    }

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

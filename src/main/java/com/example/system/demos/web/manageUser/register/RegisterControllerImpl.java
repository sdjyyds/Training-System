package com.example.system.demos.web.manageUser.register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * 注册实现类
 */
@Controller
public class RegisterControllerImpl implements RegisterController {
    // 注入RegisterService
    @Autowired
    private RegisterService registerService;

    /**
     * 注册方法
     * @param account 邮箱或者手机号
     * @param password 密码
     * @return 返回注册结果
     */
    @Override
    @ResponseBody
    @PostMapping("/register")
    public Map<String,Object> register(String account,String password) {
        Map<String, Object> result = new java.util.HashMap();
        //注册
        if (registerService.register(account, password)) {
            result.put("success", true);
            result.put("message", "注册成功！");
        } else {
            result.put("success", false);
            result.put("message", "该手机号已被注册！");
        }
        return result;
    }
}

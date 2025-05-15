package com.example.system.demos.web.manageUser.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * 重定向到登录界面
 */
@Controller
public class RedirectController {
    /**
     * 重定向到登录界面
     * @return 登录界面
     */
    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/login/login.html"; // 注意：前面加 redirect:
    }
}

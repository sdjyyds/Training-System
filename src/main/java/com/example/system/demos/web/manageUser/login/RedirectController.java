package com.example.system.demos.web.manageUser.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@Controller
public class RedirectController {

    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/login/login.html"; // 注意：前面加 redirect:
    }
}

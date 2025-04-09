package com.example.system.demos.web.manageUser.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
public interface LoginController {
    void login(HttpServletRequest request, HttpServletResponse response) throws IOException;
    String autoLogin(HttpServletRequest request);
}

package com.example.system.demos.web.manageUser.register;

import java.util.Map;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
public interface RegisterController {
    Map<String,Object> register(String content,String password);
}

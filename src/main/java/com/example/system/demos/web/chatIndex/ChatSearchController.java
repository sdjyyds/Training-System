package com.example.system.demos.web.chatIndex;

import com.example.system.jdbc.entity.ChatIndex;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * 完成对于聊天信息的查找封装
 */
public interface ChatSearchController {
    /**
     * 查找聊天信息
     * @param type user or room
     * @param id id
     * @param session 会话信息
     * @return 返回查询到的聊天
     */
    ResponseEntity<ChatIndex> search(@RequestParam String type, @RequestParam int id, HttpSession session);
}

package com.example.system.demos.web.chatIndex;

import com.example.system.jdbc.entity.ChatIndex;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * 完成对与索引界面不同跳转的业务逻辑判断的实现类
 */
@RestController
public class ChatIndexController {
    //依赖注入消息索引服务类
    @Autowired
    private ChatIndexServiceImpl chatService;

    /**
     * 显示聊天列表
     * @param userId  用户id
     * @return 返回聊天列表
     */
    @RequestMapping("/showChatList")
    public List<ChatIndex> showChatList(int userId) {
        // 交给服务层去查群聊 + 私聊对象
        return chatService.getUserChatList(userId);
    }
}

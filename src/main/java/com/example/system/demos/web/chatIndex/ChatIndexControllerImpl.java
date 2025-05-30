package com.example.system.demos.web.chatIndex;

import com.example.system.jdbc.entity.ChatIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * 完成对与索引界面不同跳转的业务逻辑判断的实现类
 */
@RestController
public class ChatIndexControllerImpl implements ChatIndexController{
    //依赖注入消息索引服务类
    @Autowired
    private ChatIndexServiceImpl chatService;

    /**
     * 显示聊天列表
     * @param request 前端请求
     * @return 返回聊天列表
     */
    @RequestMapping("/showChatList")
    public List<ChatIndex> showChatList(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        System.out.println("session: " + session);
        if (session == null || session.getAttribute("user") == null) {
            System.out.println("未登录");
            return Collections.emptyList(); // 未登录返回空
        }
        int userId = Integer.parseInt(session.getAttribute("user").toString());
        // 交给服务层去查群聊 + 私聊对象
        return chatService.getUserChatList(userId);
    }
}

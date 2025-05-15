package com.example.system.demos.web.chatIndex;

import com.example.system.jdbc.entity.ChatIndex;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * 完成对与索引界面不同跳转的业务逻辑判断的封装
 *
 */
public interface ChatIndexController {
    /**
     * 显示聊天列表
     * @param request 前端请求
     * @return 聊天列表
     */
    List<ChatIndex> showChatList(HttpServletRequest request);
}

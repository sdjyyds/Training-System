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
    List<ChatIndex> showChatList(HttpServletRequest request);
}

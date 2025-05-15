package com.example.system.demos.web.chatIndex;

import com.example.system.jdbc.entity.ChatIndex;

import java.util.List;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * 显示聊天列表的服务类
 */
public interface ChatIndexService {
    /**
     * 得到用户的聊天列表
     * @param userId 用户id
     * @return 返回用户的聊天列表
     */
    List<ChatIndex> getUserChatList(int userId);
}

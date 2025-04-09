package com.example.system.demos.web.chatIndex;

import com.example.system.jdbc.entity.ChatIndex;

import java.util.List;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
public interface ChatIndexService {
    List<ChatIndex> getUserChatList(int userId);
}

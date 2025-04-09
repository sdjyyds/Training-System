package com.example.system.demos.web.chatIndex;


import com.example.system.jdbc.entity.ChatIndex;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
public interface ChatSearchService {
    ChatIndex getPrivateChatIndexByUserId(int useId);
    ChatIndex getGroupChatIndexByRoomId(int roomId);
}

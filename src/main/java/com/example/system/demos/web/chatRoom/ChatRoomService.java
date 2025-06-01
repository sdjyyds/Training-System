package com.example.system.demos.web.chatRoom;

import com.example.system.jdbc.entity.ChatRoom;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * 聊天服务的封装类
 */
public interface ChatRoomService {

    boolean addChatRoom(ChatRoom chatRoom);

    boolean deleteChatRoom(int roomId);
    boolean updateChatRoomName(int roomId, String newName, String chatImage);

}

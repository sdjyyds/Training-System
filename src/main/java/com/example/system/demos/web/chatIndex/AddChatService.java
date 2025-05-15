package com.example.system.demos.web.chatIndex;

import com.example.system.jdbc.entity.ChatRoom;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * 聊天服务的封装类
 */
public interface AddChatService {
    /**
     * 添加私聊的服务
     *
     * @param senderId   发送者的id
     * @param receiverId 接受者的id
     * @param content    聊天内容
     */
    void addPrivateChat(int senderId, int receiverId, String content);

    /**
     * 添加群聊
     *
     * @param senderId 发送者的id
     * @param roomId   群聊号
     * @param content  聊天内容
     */
    void addGroupChat(int senderId, int roomId, String content);

    boolean addChatRoom(ChatRoom chatRoom);

    boolean deleteChatRoom(int roomId);
    boolean updateChatRoomName(int roomId, String newName, String chatImage);

}

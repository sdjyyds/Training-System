package com.example.system.demos.web.chatIndex;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
public interface AddChatService {
    void addPrivateChat(int senderId, int receiverId,String content);
    void addGroupChat(int senderId, int roomId,String content);
    Boolean createRoom(String roomName,String roomImage);
}

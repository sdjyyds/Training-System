package com.example.system.demos.web.chat.showChat;


import com.example.system.jdbc.entity.Message;

import java.util.List;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
public interface ChatService {
    List<Message> getGroupMessages(int roomId);
    void savePrivateMessage(int senderId, int receiverId, String content);
    String getUsernameById(int id);
    void saveGroupMessage(int senderId, int roomId, String content);

    List<Message> getPrivateMessages(int userId,int myUserId);
}

package com.example.system.demos.web.chatIndex;

import javax.servlet.http.HttpSession;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
public interface AddChatController {
    String addPrivateChat(int receiverId, String content, HttpSession session);
    String addGroupChat(int roomId, String content, HttpSession session);
    Boolean createRoom(String roomName,String roomImage);
}

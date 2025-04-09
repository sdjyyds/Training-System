package com.example.system.demos.web.chat.showChat;

import com.example.system.jdbc.entity.Message;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
public interface ShowChatController {
    List<Message> getGroupMessages(int roomId, HttpSession session);
    List<Message> getPrivateMessages(int userId, HttpSession session);
}

package com.example.system.demos.web.chat.showChat;

import com.example.system.jdbc.entity.Message;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 用来封装不同的方式得到群聊消息和私聊消息
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
public interface ShowChatController {
    /**
     * 得到群聊消息
     *
     * @param roomId 群聊号
     * @param session 用来得到会话中标注的用户Id
     * @return 返回群聊消息
     */
    List<Message> getGroupMessages(int roomId, HttpSession session);

    /**
     * 得到私聊消息
     *
     * @param userId 对方的用户id
     * @param session 用来得到会话中标注的用户Id
     * @return 返回私聊消息
     */
    List<Message> getPrivateMessages(int userId, HttpSession session);
}

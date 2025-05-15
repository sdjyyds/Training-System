package com.example.system.demos.web.chatIndex;

import com.example.system.jdbc.entity.ChatRoom;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * 添加聊天的封装类
 */
public interface AddChatController {
    /**
     * 添加私聊
     * @param receiverId 发送者的id
     * @param content 消息
     * @param session 会话
     * @return 返回消息
     */
    String addPrivateChat(int receiverId, String content, HttpSession session);

    /**
     * 添加群聊
     *
     * @param roomId 房间号
     * @param content 消息
     * @param session 会话
     * @return 返回是否成功
     */
    String addGroupChat(int roomId, String content, HttpSession session);

    Map<String, Object> renameRoom(int roomId, String newName,String chatImage);
    Map<String, Object> deleteRoom(int roomId);
    Map<String, Object> addRoom(ChatRoom chatRoom);
}

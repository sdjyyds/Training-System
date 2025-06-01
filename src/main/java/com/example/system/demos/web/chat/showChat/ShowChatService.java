package com.example.system.demos.web.chat.showChat;


import com.example.system.jdbc.entity.Message;
import java.util.List;

/**
 * 用来封装不同的控制类需要的不同的服务类
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
public interface ShowChatService {
    /**
     * 得到群聊消息
     * @param roomId 群聊号
     * @return 返回群聊消息
     */
    List<Message> getGroupMessages(int roomId);

    /**
     * 保存私聊消息
     * @param senderId 发送方
     * @param receiverId 接收方
     * @param content 消息
     */
    void savePrivateMessage(int senderId, int receiverId, String content);

    /**
     * 通过用户id得到用户名
     * @param id 用户id
     * @return 返回用户名
     */
    String getUsernameById(int id);

    /**
     * 保存群聊消息
     * @param senderId 接收方
     * @param roomId 群聊号
     * @param content 消息
     */
    void saveGroupMessage(int senderId, int roomId, String content);

    /**
     * 得到私聊消息
     * @param userId 对方的用户id
     * @param myUserId 当前用户
     * @return 返回私聊消息
     */
    List<Message> getPrivateMessages(int userId,int myUserId);
}

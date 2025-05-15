package com.example.system.demos.web.chat.showChat;

import com.example.system.jdbc.dao.GroupMessageDao;
import com.example.system.jdbc.dao.PrivateChatDao;
import com.example.system.jdbc.dao.UserDao;
import com.example.system.jdbc.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 服务ShowChatControllerImpl类,完成对Dao层的调用
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@Service
public class ShowChatServiceImpl implements ShowChatService {
    //依赖注入
    @Autowired
    private PrivateChatDao privateChatDao;
    @Autowired
    private GroupMessageDao groupMessageDao;
    @Autowired
    private UserDao userDao;

    /**
     * 使用groupMessageDao获取群聊消息
     * @param roomId 群聊号
     * @return 返回群聊消息
     */
    @Override
    public List<Message> getGroupMessages(int roomId) {
        return groupMessageDao.showGroupChatMessage(roomId);
    }

    /**
     * 使用privateChatDao获取私聊消息
     * @param userId 对方的用户id
     * @param myUserId 当前用户
     * @return 返回私聊消息
     */
    @Override
    public List<Message> getPrivateMessages(int userId, int myUserId) {
        return privateChatDao.showPrivateChatMessage(userId, myUserId);
    }

    /**
     * 保存私聊消息
     * @param senderId 发送方
     * @param receiverId 接收方
     * @param content 消息
     */
    @Override
    public void savePrivateMessage(int senderId, int receiverId, String content) {
        privateChatDao.insertPrivateMessage(senderId, receiverId, content);
    }

    /**
     * 根据用户id获取用户名
     * @param id 用户id
     * @return 返回用户名
     */
    @Override
    public String getUsernameById(int id) {
        return userDao.selectUserNameById(id);
    }

    /**
     * 保存群聊消息
     * @param senderId 接收方
     * @param roomId 群聊号
     * @param content 消息
     */
    @Override
    public void saveGroupMessage(int senderId, int roomId, String content) {
        groupMessageDao.insertGroupMessage(senderId, roomId, content);
    }
}
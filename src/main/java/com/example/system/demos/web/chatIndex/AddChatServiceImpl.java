package com.example.system.demos.web.chatIndex;

import com.example.system.jdbc.dao.ChatRoomDao;
import com.example.system.jdbc.dao.GroupMessageDao;
import com.example.system.jdbc.dao.PrivateChatDao;
import com.example.system.jdbc.entity.ChatRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * 添加聊天服务的实现类
 */
@Service
public class AddChatServiceImpl implements AddChatService {
    //注入私聊操作类
    @Autowired
    private PrivateChatDao privateChatDao;
    //注入群聊操作类
    @Autowired
    private GroupMessageDao groupMessageDao;
    //注入聊天室操作类
    @Autowired
    private ChatRoomDao chatRoomDao;

    /**
     * 添加私聊
     * @param senderId 发送者的id
     * @param receiverId 接受者的id
     * @param content 聊天内容
     */
    @Override
    public void addPrivateChat(int senderId, int receiverId, String content) {
        privateChatDao.insertPrivateMessage(senderId, receiverId, content);
    }

    /**
     * 添加群聊
     * @param senderId 发送者的id
     * @param roomId 群聊号
     * @param content 聊天内容
     */
    @Override
    public void addGroupChat(int senderId, int roomId, String content) {
        groupMessageDao.insertGroupMessage(senderId, roomId, content);
    }

    @Override
    public boolean deleteChatRoom(int roomId) {
        groupMessageDao.deleteByRoomId(roomId);
        return chatRoomDao.deleteById(roomId) > 0;
    }

    @Override
    public boolean addChatRoom(ChatRoom chatRoom) {
        if (chatRoom.getChatImage() == null || chatRoom.getChatImage().trim().isEmpty()) {
            chatRoom.setChatImage("../img/default-avatar.jpg"); // 默认头像
        }
        return chatRoomDao.insert(chatRoom) > 0;
    }

    @Override
    public boolean updateChatRoomName(int roomId, String newName, String chatImage) {
        return chatRoomDao.updateNameAndImageById(roomId, newName, chatImage) > 0;
    }

}

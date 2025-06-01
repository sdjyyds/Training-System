package com.example.system.demos.web.chatRoom;

import com.example.system.jdbc.dao.ChatRoomDao;
import com.example.system.jdbc.dao.GroupMessageDao;
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
public class ChatRoomServiceImpl implements ChatRoomService {
    //注入群聊操作类
    @Autowired
    private GroupMessageDao groupMessageDao;
    //注入聊天室操作类
    @Autowired
    private ChatRoomDao chatRoomDao;

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

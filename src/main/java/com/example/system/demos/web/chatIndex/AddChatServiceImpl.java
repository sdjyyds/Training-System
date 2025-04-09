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
 */
@Service
public class AddChatServiceImpl implements AddChatService {
    @Autowired
    private PrivateChatDao privateChatDao;
    @Autowired
    private GroupMessageDao groupMessageDao;
    @Autowired
    private ChatRoomDao chatRoomDao;
    @Override
    public void addPrivateChat(int senderId, int receiverId, String content) {
        privateChatDao.insertPrivateMessage(senderId, receiverId, content);
    }

    @Override
    public void addGroupChat(int senderId, int roomId, String content) {
        groupMessageDao.insertGroupMessage(senderId, roomId, content);
    }

    @Override
    public Boolean createRoom(String roomName, String roomImage) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setName(roomName);
        chatRoom.setChatImage(roomImage);
        return chatRoomDao.insert(chatRoom) > 0;
    }
}

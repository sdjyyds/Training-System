package com.example.system.demos.web.chat.showChat;

import com.example.system.jdbc.dao.GroupMessageDao;
import com.example.system.jdbc.dao.PrivateChatDao;
import com.example.system.jdbc.dao.UserDao;
import com.example.system.jdbc.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@Service
public class ShowChatServiceImpl implements ShowChatService {
    @Autowired
    private PrivateChatDao privateChatDao;
    @Autowired
    private GroupMessageDao groupMessageDao;
    @Autowired
    private UserDao userDao;
    @Override
    public List<Message> getGroupMessages(int roomId) {
        List<Message> messageList = new ArrayList();
        messageList.addAll(groupMessageDao.showGroupChatMessage(roomId));
        return messageList;
    }

    @Override
    public List<Message> getPrivateMessages(int userId, int myUserId) {
        List<Message> messageList = new ArrayList();
        messageList.addAll(privateChatDao.showPrivateChatMessage(userId, myUserId));
        return messageList;
    }

    @Override
    public void savePrivateMessage(int senderId, int receiverId, String content) {
        privateChatDao.insertPrivateMessage(senderId, receiverId, content);
    }

    @Override
    public String getUsernameById(int id) {
        return userDao.selectUserNameById(id);
    }

    @Override
    public void saveGroupMessage(int senderId, int roomId, String content) {
        groupMessageDao.insertGroupMessage(senderId, roomId, content);
    }
}
/*
1.保留私有消息 -- 直接放入私有表
insert into private_chat(sender_id,receiver_id,message,timestamp)
values(#{senderId},#{receiverId},#{content},now());

2.保留群消息
insert into group_message(sender_id,room_id,message,timestamp)
values(#{senderId},#{roomId},#{content},now());
 */
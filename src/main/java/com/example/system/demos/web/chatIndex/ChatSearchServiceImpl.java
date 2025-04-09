package com.example.system.demos.web.chatIndex;

import com.example.system.jdbc.dao.ChatRoomDao;
import com.example.system.jdbc.dao.UserDao;
import com.example.system.jdbc.entity.ChatIndex;
import com.example.system.jdbc.entity.ChatRoom;
import com.example.system.jdbc.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 */
@Service
public class ChatSearchServiceImpl implements ChatSearchService{
    @Autowired
    private ChatRoomDao chatRoomDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ChatIndex chatIndex;
    @Override
    public ChatIndex getPrivateChatIndexByUserId(int useId) {
        User user = userDao.selectByPrimaryKey(useId);
        if(user == null) return null;
        chatIndex.setChatImage(user.getUserImage());
        chatIndex.setId(user.getId());
        chatIndex.setChatName(user.getUsername());
        chatIndex.setType("privateChat");
        System.out.println(chatIndex);
        return chatIndex;
    }

    @Override
    public ChatIndex getGroupChatIndexByRoomId(int roomId) {
        ChatRoom chatRoom = chatRoomDao.selectByPrimaryKey(roomId);
        if(chatRoom == null) return null;
        chatIndex.setChatImage(chatRoom.getChatImage());
        chatIndex.setId(chatRoom.getId());
        chatIndex.setChatName(chatRoom.getName());
        chatIndex.setType("roomChat");
        System.out.println(chatIndex);
        return chatIndex;
    }
}

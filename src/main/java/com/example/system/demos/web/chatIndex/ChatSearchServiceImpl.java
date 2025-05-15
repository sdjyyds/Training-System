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
 * 完成对与索引界面不同跳转的业务逻辑判断的实现
 */
@Service
public class ChatSearchServiceImpl implements ChatSearchService{
    //需要操作的房间
    @Autowired
    private ChatRoomDao chatRoomDao;
    //需要操作的用户
    @Autowired
    private UserDao userDao;
    //需要操作的索引
    @Autowired
    private ChatIndex chatIndex;

    /**
     * 根据用户的id获取私聊的索引
     * @param useId 用户的id
     * @return 返回私聊
     */
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

    /**
     * 根据群聊的id获取群聊的索引
     * @param roomId 群聊号
     * @return 返回群聊
     */
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

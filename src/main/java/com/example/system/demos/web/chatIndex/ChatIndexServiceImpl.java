package com.example.system.demos.web.chatIndex;

import com.example.system.jdbc.dao.UserDao;
import com.example.system.jdbc.entity.ChatIndex;
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
public class ChatIndexServiceImpl implements  ChatIndexService {
    @Autowired
    private UserDao userDao;

    public List<ChatIndex> getUserChatList(int userId) {
        List<ChatIndex> chatIndexList = new ArrayList();
        //私聊
        chatIndexList.addAll(userDao.selectPrivateChatUser(userId));
        //群聊
        chatIndexList.addAll(userDao.selectChatRoomsName(userId));
        return chatIndexList;
    }
}


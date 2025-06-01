package com.example.system.demos.web.chatIndex;

import com.example.system.jdbc.dao.ChatRoomDao;
import com.example.system.jdbc.dao.UserDao;
import com.example.system.jdbc.entity.ChatIndex;
import com.example.system.jdbc.entity.ChatRoom;
import com.example.system.jdbc.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * 完成对与索引界面不同跳转的业务逻辑判断的实现
 */
@Service
@Slf4j
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
        log.info("开始获取用户{}的私聊索引", useId);
        User user = userDao.selectByPrimaryKey(useId);
        if(user == null) {
            log.error("获取用户{}的私聊索引失败，用户不存在", useId);
            return null;
        }
        chatIndex.setChatImage(user.getUserImage());
        chatIndex.setId(user.getId());
        chatIndex.setChatName(user.getUsername());
        chatIndex.setType("privateChat");
        log.info("获取用户{}的私聊索引成功，索引：{}", useId, chatIndex);
        return chatIndex;
    }

    /**
     * 根据群聊的id获取群聊的索引
     * @param roomId 群聊号
     * @return 返回群聊
     */
    @Override
    public ChatIndex getGroupChatIndexByRoomId(int roomId) {
        log.info("开始获取群聊{}的索引", roomId);
        ChatRoom chatRoom = chatRoomDao.selectByPrimaryKey(roomId);
        if(chatRoom == null) {
            log.error("获取群聊{}的索引失败，群聊不存在", roomId);
            return null;
        }
        chatIndex.setChatImage(chatRoom.getChatImage());
        chatIndex.setId(chatRoom.getId());
        chatIndex.setChatName(chatRoom.getName());
        chatIndex.setType("roomChat");
        log.info("获取群聊{}的索引成功，索引：{}", roomId, chatIndex);
        return chatIndex;
    }
}
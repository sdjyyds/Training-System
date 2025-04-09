package com.example.system.jdbc.dao;

import com.example.system.jdbc.entity.ChatIndex;
import com.example.system.jdbc.entity.ChatRoom;

public interface ChatRoomDao {
    int deleteByPrimaryKey(Integer id);

    int insert(ChatRoom record);

    int insertSelective(ChatRoom record);

    ChatRoom selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(ChatRoom record);

    int updateByPrimaryKey(ChatRoom record);
}
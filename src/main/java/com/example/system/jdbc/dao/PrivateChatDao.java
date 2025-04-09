package com.example.system.jdbc.dao;

import com.example.system.jdbc.entity.Message;
import com.example.system.jdbc.entity.PrivateChat;
import org.apache.ibatis.annotations.Param;

import java.util.List;
public interface PrivateChatDao {
    int deleteByPrimaryKey(Integer id);

    int insert(PrivateChat record);

    int insertSelective(PrivateChat record);

    PrivateChat selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PrivateChat record);

    int updateByPrimaryKeyWithBLOBs(PrivateChat record);

    int updateByPrimaryKey(PrivateChat record);
    List<Message> showPrivateChatMessage(@Param("userId")Integer userId,@Param("myUserId") Integer myUserId);
    int insertPrivateMessage(@Param("senderId") int senderId,@Param("receiverId") int receiverId,@Param("content") String content);
}
package com.example.system.jdbc.dao;

import com.example.system.jdbc.entity.GroupMessage;
import com.example.system.jdbc.entity.Message;
import org.apache.ibatis.annotations.Param;

import java.util.List;
public interface GroupMessageDao {
    int deleteByPrimaryKey(Integer id);

    int insert(GroupMessage record);

    int insertSelective(GroupMessage record);

    GroupMessage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GroupMessage record);

    int updateByPrimaryKeyWithBLOBs(GroupMessage record);
    List<Message> showGroupChatMessage(@Param("roomId") Integer roomId);
    int updateByPrimaryKey(GroupMessage record);
    int insertGroupMessage(@Param("senderId") int senderId,@Param("roomId") int roomId,@Param("content") String content);
}
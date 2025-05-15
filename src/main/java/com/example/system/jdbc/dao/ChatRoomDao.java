package com.example.system.jdbc.dao;

import com.example.system.jdbc.entity.ChatRoom;
import org.apache.ibatis.annotations.Param;

/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * 与ChatRoom表对应
 */
public interface ChatRoomDao {
    /**
     * 将chatRoom插入数据库
     * @param record chatRoom实体
     * @return 返回插入的行数
     */
    int insert(ChatRoom record);

    /**
     * 选择性的将chatRoom插入数据库
     * @param record chatRoom实体
     * @return 返回插入的行数
     */
    int insertSelective(ChatRoom record);

    /**
     * 根据id字段查找对应的chatRoom
     * @param id 根据id字段
     * @return 返回chatRoom
     */
    ChatRoom selectByPrimaryKey(Integer id);
    /**
     * 根据id字段删除对应的chatRoom
     * @param roomId 根据id字段
     * @return 返回删除的行数
     */
    int deleteById(int roomId);
    int updateNameAndImageById(@Param("roomId") int roomId,@Param("newName") String newName,@Param("chatImage") String chatImage);

}
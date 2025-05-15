package com.example.system.jdbc.dao;

import com.example.system.jdbc.entity.GroupMessage;
import com.example.system.jdbc.entity.Message;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 *
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * 与数据库中的group_messages表对应
 */
public interface GroupMessageDao {
    /**
     * 根据id删除对应表
     * @param id group_messages的id
     * @return 返回删除的行数
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 将group_messages插入数据库
     * @param record group_messages的实体
     * @return 返回插入的行数
     */
    int insert(GroupMessage record);

    /**
     * 选择性的将group_messages插入数据库
     * @param record group_messages的实体
     * @return 返回插入的行数
     */
    int insertSelective(GroupMessage record);

    /**
     * 根据id查找对应的group_messages
     * @param id group_messages的id
     * @return 返回group_messages
     */
    GroupMessage selectByPrimaryKey(Integer id);

    /**
     * 选择性的更新group_messages
     * @param record group_messages的实体
     * @return 返回更新的行数
     */
    int updateByPrimaryKeySelective(GroupMessage record);

    /*
     * 更新group_messages
     * @param record group_messages的实体
     * @return 返回更新的行数
    int updateByPrimaryKeyWithBLOBs(GroupMessage record);
    */

    /**
     * 根据roomId查找群聊消息
     * @param roomId 群聊号
     * @return 返回群聊消息
     */
    List<Message> showGroupChatMessage(@Param("roomId") Integer roomId);

    /**
     * 更新group_messages
     * @param record group_messages的实体
     * @return 返回更新的行数
     */
    int updateByPrimaryKey(GroupMessage record);

    /**
     * 插入群聊消息
     * @param senderId 发送者id
     * @param roomId 群聊号
     * @param content 消息
     * @return 返回插入的行数
     */
    int insertGroupMessage(@Param("senderId") int senderId,@Param("roomId") int roomId,@Param("content") String content);
    /**
     * 根据群聊号删除群聊消息
     * @param roomId 群聊号
     * @return 返回删除的行数
     */
    int deleteByRoomId(int roomId);
}
package com.example.system.jdbc.dao;

import com.example.system.jdbc.entity.Message;
import com.example.system.jdbc.entity.PrivateChat;
import org.apache.ibatis.annotations.Param;
import java.util.List;
/**
 * @author jds
 * @version 1.1
 * @since 1.0.0
 * 与数据库中的private_chat表对应
 */
public interface PrivateChatDao {
    /**
     * 根据主键删除
     * @param id private_chat的id
     * @return 返回删除的行数
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入private_chat的实体
     * @param record private_chat的实体
     * @return 返回插入的行数
     */
    int insert(PrivateChat record);

    /**
     * 选择性的插入private_chat
     * @param record private_chat的实体
     * @return 返回插入的行数
     */
    int insertSelective(PrivateChat record);

    /**
     * 根据主键查找private_chat
     * @param id private_chat的id
     * @return 返回private_chat
     */
    PrivateChat selectByPrimaryKey(Integer id);

    /**
     * 选择性的更新private_chat
     * @param record private_chat的实体
     * @return 返回更新的行数
     */
    int updateByPrimaryKeySelective(PrivateChat record);

    /*
        更新大数据的操作
       int updateByPrimaryKeyWithBLOBs(PrivateChat record);
     */

    /**
     * 更新private_chat
     * @param record private_chat的实体
     * @return 返回更新的行数
     */
    int updateByPrimaryKey(PrivateChat record);

    /**
     * 显示私聊信息
     * @param userId 对方的用户id
     * @param myUserId 当前用户的id
     * @return 返回私聊信息
     */
    List<Message> showPrivateChatMessage(@Param("userId")Integer userId,@Param("myUserId") Integer myUserId);

    /**
     * 插入私聊信息
     * @param senderId 发送者的id
     * @param receiverId 接受者的id
     * @param content 消息
     * @return 返回插入的行数
     */
    int insertPrivateMessage(@Param("senderId") int senderId,@Param("receiverId") int receiverId,@Param("content") String content);
}